package net.dungeonhub

import com.google.gson.JsonObject
import net.dungeonhub.cache.disk.DiskHistoryCache
import net.dungeonhub.hypixel.client.DiskCacheApiClient
import net.dungeonhub.hypixel.entities.guild.Guild
import net.dungeonhub.hypixel.entities.player.toHypixelPlayer
import net.dungeonhub.hypixel.entities.skyblock.CurrentMember
import net.dungeonhub.hypixel.entities.skyblock.SkyblockProfile
import net.dungeonhub.provider.GsonProvider
import net.dungeonhub.service.TestHelper
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.assertDoesNotThrow
import java.io.File
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class TestDiskCache {
    @Test
    fun testDiskCacheSaving() {
        val apiClient = DiskCacheApiClient

        val rawData = listOf(
            UUID.fromString("1686c45d-f082-4811-b1c8-b1db7810e255") to TestHelper.readFile("player-data/1686c45d-f082-4811-b1c8-b1db7810e255.json"),
            UUID.fromString("39642ffc-a7fb-4d24-a1d4-916f4cad1d98") to TestHelper.readFile("player-data/39642ffc-a7fb-4d24-a1d4-916f4cad1d98.json")
        )

        val links = listOf(
            UUID.fromString("1686c45d-f082-4811-b1c8-b1db7810e255") to "sirdowntime",
            UUID.fromString("39642ffc-a7fb-4d24-a1d4-916f4cad1d98") to "taubsie"
        )

        //Check if cache is empty
        for (pair in rawData) {
            assertNull(apiClient.getPlayerData(pair.first))
        }

        for (pair in links) {
            assertNull(apiClient.getHypixelLinkedDiscord(pair.first))
        }

        assertEquals(0, apiClient.playerDataCache.retrieveAllElements().count())

        //Store example data in cache
        for (pair in rawData) {
            val player = GsonProvider.gson.fromJson(pair.second, JsonObject::class.java)

            apiClient.playerDataCache.store(player.toHypixelPlayer())
        }

        //Check if cache is filled
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
    fun testDiskCacheSkyblock() {
        val apiClient = DiskCacheApiClient

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

    companion object {
        @JvmStatic
        @BeforeAll
        fun build() {
            DiskHistoryCache.cacheDirectory =
                "${System.getProperty("user.home")}${File.separator}dungeon-hub${File.separator}hypixel-wrapper-test"
        }

        @JvmStatic
        @AfterAll
        fun tearDown() {
            DiskCacheApiClient.clearCache()
        }
    }
}