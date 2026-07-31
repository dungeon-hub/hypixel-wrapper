package net.dungeonhub

import com.google.gson.JsonObject
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.mongo.transitions.Mongod
import de.flapdoodle.embed.mongo.transitions.RunningMongodProcess
import de.flapdoodle.reverse.TransitionWalker
import com.google.gson.reflect.TypeToken
import net.dungeonhub.cache.CacheType
import net.dungeonhub.cache.database.MongoCache
import net.dungeonhub.cache.memory.CacheElement
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
import java.time.Instant
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
            assertNull(apiClient.getPlayerData(pair.first).valueOrNull)
        }

        for (pair in links) {
            assertNull(apiClient.getHypixelLinkedDiscord(pair.first).valueOrNull)
        }

        assertEquals(0, apiClient.playerDataCache.retrieveAllElements().count())

        for (pair in rawData) {
            val player = GsonProvider.gson.fromJson(pair.second, JsonObject::class.java)

            apiClient.playerDataCache.store(player.toHypixelPlayer(), waitForInsertion = true)
        }

        for (pair in rawData) {
            assertNotNull(apiClient.getPlayerData(pair.first).valueOrNull)
        }

        for (pair in links) {
            assertEquals(pair.second, apiClient.getHypixelLinkedDiscord(pair.first).valueOrNull)
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


    @Test
    fun testMongoHistoryCacheRetrievesValueBeforeOrAfterTimestamp() {
        val cache = MongoCache(
            MongoCacheProvider.getCollection("historical-cache"),
            object : TypeToken<CacheElement<HistoricalValue>>() {},
            { it.key },
            memoryCacheSize = 0
        )
        cache.storeCacheElement(CacheElement(Instant.parse("2024-01-01T00:00:00Z"), HistoricalValue("profile", "first")))
        cache.storeCacheElement(CacheElement(Instant.parse("2024-01-02T00:00:00Z"), HistoricalValue("profile", "second")))
        cache.storeCacheElement(CacheElement(Instant.parse("2024-01-03T00:00:00Z"), HistoricalValue("profile", "third")))

        assertEquals("second", cache.retrieveAt("profile", before = Instant.parse("2024-01-02T12:00:00Z"))?.payload)
        assertEquals("second", cache.retrieveAt("profile", after = Instant.parse("2024-01-01T12:00:00Z"))?.payload)
        assertEquals(
            "second",
            cache.retrieveAt(
                "profile",
                before = Instant.parse("2024-01-02T12:00:00Z"),
                after = Instant.parse("2024-01-01T12:00:00Z")
            )?.payload
        )
        assertNull(cache.retrieveAt("profile", before = Instant.parse("2023-12-31T23:59:59Z")))
    }

    data class HistoricalValue(val key: String, val payload: String)

    @BeforeEach
    fun cleanBefore() {
        cleanCollections()
    }

    @AfterEach
    fun cleanAfter() {
        cleanCollections()
    }

    companion object {
        private var running: TransitionWalker.ReachedState<RunningMongodProcess>? = null

        @JvmStatic
        @BeforeAll
        fun startEmbeddedMongo() {
            val mongodConfig = Mongod.instance()
            val version = Version.Main.V8_2

            running = mongodConfig.start(version)
            val serverAddress = running?.current()?.serverAddress

            MongoCacheProvider.connectionString = "mongodb://$serverAddress"
            MongoCacheProvider.databaseName = "test-${UUID.randomUUID()}"
            MongoCacheProvider.collectionPrefix = "test"
        }

        @JvmStatic
        @AfterAll
        fun stopEmbeddedMongo() {
            cleanCollections()

            if (running != null) { runCatching { running?.close() } }
            running = null

            MongoCacheProvider.connectionString = null
        }

        private fun cleanCollections() {
            if (!MongoCacheProvider.isConfigured) {
                return
            }
            CachedResource.entries.forEach { resource ->
                runCatching { MongoCacheProvider.getCollection(resource.resourceName).drop() }
            }
            runCatching { MongoCacheProvider.getCollection("historical-cache").drop() }
        }
    }
}