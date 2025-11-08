package net.dungeonhub

import com.google.gson.JsonObject
import de.flapdoodle.embed.mongo.MongodExecutable
import de.flapdoodle.embed.mongo.MongodProcess
import de.flapdoodle.embed.mongo.MongodStarter
import de.flapdoodle.embed.mongo.config.MongodConfig
import de.flapdoodle.embed.mongo.config.Net
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.process.runtime.Network
import net.dungeonhub.cache.CacheType
import net.dungeonhub.cache.database.MongoCacheProvider
import net.dungeonhub.hypixel.client.CacheApiClient
import net.dungeonhub.hypixel.client.CachedResource
import net.dungeonhub.hypixel.entities.guild.Guild
import net.dungeonhub.hypixel.entities.player.toHypixelPlayer
import net.dungeonhub.hypixel.entities.skyblock.CurrentMember
import net.dungeonhub.hypixel.entities.skyblock.SkyblockProfile
import net.dungeonhub.provider.GsonProvider
import net.dungeonhub.service.TestHelper
import org.junit.jupiter.api.*
import java.net.InetAddress
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class TestMongoCache {

    @Test
    fun testDatabaseCacheSaving() {
        val apiClient = CacheApiClient(CacheType.Database)

        val rawData = listOf(
            UUID.fromString("1686c45d-f082-4811-b1c8-b1db7810e255") to TestHelper.readFile("player-data/1686c45d-f082-4811-b1c8-b1db7810e255.json"),
            UUID.fromString("39642ffc-a7fb-4d24-a1d4-916f4cad1d98") to TestHelper.readFile("player-data/39642ffc-a7fb-4d24-a1d4-916f4cad1d98.json")
        )

        val links = listOf(
            UUID.fromString("1686c45d-f082-4811-b1c8-b1db7810e255") to "sirdowntime",
            UUID.fromString("39642ffc-a7fb-4d24-a1d4-916f4cad1d98") to "taubsie"
        )

        for (pair in rawData) {
            assertNull(apiClient.getPlayerData(pair.first))
        }

        for (pair in links) {
            assertNull(apiClient.getHypixelLinkedDiscord(pair.first))
        }

        assertEquals(0, apiClient.playerDataCache.retrieveAllElements().count())

        for (pair in rawData) {
            val player = GsonProvider.gson.fromJson(pair.second, JsonObject::class.java)

            apiClient.playerDataCache.store(player.toHypixelPlayer())
        }

        for (pair in rawData) {
            assertNotNull(apiClient.getPlayerData(pair.first))
        }

        for (pair in links) {
            assertEquals(pair.second, apiClient.getHypixelLinkedDiscord(pair.first))
        }

        assertEquals(2, apiClient.playerDataCache.retrieveAllElements().count())
    }

    @Test
    fun testSerialization() {
        TestHelper.runParallel {
            TestHelper.readAllSkyblockProfiles().parallel().forEach { skyblockProfiles ->
                for (profile in skyblockProfiles) {
                    val profileJson = GsonProvider.gson.toJson(profile)

                    val profileFromJson = GsonProvider.gson.fromJson(profileJson, SkyblockProfile::class.java)

                    assertNotNull(profileFromJson)
                }
            }
        }

        for (guild in TestHelper.readAllGuilds()) {
            val guildJson = GsonProvider.gson.toJson(guild)

            val guildFromJson = GsonProvider.gson.fromJson(guildJson, Guild::class.java)

            assertNotNull(guildFromJson)
        }
    }

    @Test
    fun testDatabaseCacheSkyblock() {
        val apiClient = CacheApiClient(CacheType.Database)

        TestHelper.runParallel {
            TestHelper.readAllSkyblockProfileObjects().parallel().forEach { skyblockProfiles ->
                assertDoesNotThrow { apiClient.skyblockProfilesCache.store(skyblockProfiles) }

                val loadedProfile =
                    assertDoesNotThrow { apiClient.skyblockProfilesCache.retrieve(skyblockProfiles.owner) }

                assertNotNull(loadedProfile)

                for (profile in loadedProfile.profiles) {
                    for (member in profile.members.filterIsInstance<CurrentMember>()) {
                        for (inventoryContent in member.inventory?.allItems ?: listOf()) {
                            assertNotNull(inventoryContent.items)
                        }
                    }
                }
            }
        }
    }

    @BeforeEach
    fun cleanBefore() {
        cleanCollections()
    }

    @AfterEach
    fun cleanAfter() {
        cleanCollections()
    }

    companion object {
        private lateinit var mongodExecutable: MongodExecutable
        private var mongodProcess: MongodProcess? = null

        @JvmStatic
        @BeforeAll
        fun startEmbeddedMongo() {
            Assumptions.assumeFalse(System.getenv("CI") == "true", "Skipping embedded MongoDB tests in CI/CD environment due to an unknown incompatibility")

            val port = Network.freeServerPort(InetAddress.getByName("localhost"))
            val ipv6 = runCatching { Network.localhostIsIPv6() }.getOrDefault(false)

            val net = Net("127.0.0.1", port, ipv6)

            val config = MongodConfig.builder()
                .version(Version.Main.PRODUCTION)
                .net(net)
                .build()

            val starter = MongodStarter.getDefaultInstance()
            mongodExecutable = starter.prepare(config)
            mongodProcess = mongodExecutable.start()

            MongoCacheProvider.connectionString = "mongodb://localhost:$port"
            MongoCacheProvider.databaseName = "test-${UUID.randomUUID()}"
            MongoCacheProvider.collectionPrefix = "test"
        }

        @JvmStatic
        @AfterAll
        fun stopEmbeddedMongo() {
            cleanCollections()

            runCatching { mongodProcess?.stop() }
            runCatching { mongodExecutable.stop() }
            MongoCacheProvider.connectionString = null
        }

        private fun cleanCollections() {
            if (!MongoCacheProvider.isConfigured) {
                return
            }
            CachedResource.entries.forEach { resource ->
                runCatching { MongoCacheProvider.getCollection(resource.resourceName).drop() }
            }
        }
    }
}