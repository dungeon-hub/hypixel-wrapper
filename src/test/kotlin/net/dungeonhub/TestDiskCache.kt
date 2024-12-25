package net.dungeonhub

import com.google.gson.Gson
import com.google.gson.JsonObject
import net.dungeonhub.cache.disk.DiskHistoryCache
import net.dungeonhub.hypixel.client.DiskCacheApiClient
import net.hypixel.api.reply.PlayerReply.Player
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
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
            UUID.fromString("1686c45d-f082-4811-b1c8-b1db7810e255") to "{\"uuid\": \"1686c45df0824811b1c8b1db7810e255\",\"playername\": \"sirdowntime\",\"displayname\": \"SirDowntime\",\"socialMedia\": {\"links\": {\"DISCORD\": \"sirdowntime\"},\"prompt\": true}}",
            UUID.fromString("39642ffc-a7fb-4d24-a1d4-916f4cad1d98") to "{\"uuid\": \"39642ffca7fb4d24a1d4916f4cad1d98\",\"playername\": \"taubsie\",\"displayname\": \"Taubsie\",\"socialMedia\": {\"links\": {\"DISCORD\": \"taubsie\"},\"prompt\": true}}"
        )

        val links = listOf(
            UUID.fromString("1686c45d-f082-4811-b1c8-b1db7810e255") to "sirdowntime",
            UUID.fromString("39642ffc-a7fb-4d24-a1d4-916f4cad1d98") to "taubsie"
        )

        //Check if cache is empty
        for (pair in rawData) {
            assertNull(apiClient.getPlayerData(pair.first))
        }

        for(pair in links) {
            assertNull(apiClient.getHypixelLinkedDiscord(pair.first))
        }

        assertEquals(0, apiClient.playerDataCache.retrieveAllElements().count())

        //Store example data in cache
        for (pair in rawData) {
            val player = Gson().fromJson(pair.second, JsonObject::class.java)

            apiClient.playerDataCache.store(Player(player))
        }

        //Check if cache is filled
        for (pair in rawData) {
            assertNotNull(apiClient.getPlayerData(pair.first))
        }

        for(pair in links) {
            assertEquals(apiClient.getHypixelLinkedDiscord(pair.first), pair.second)
        }

        assertEquals(apiClient.playerDataCache.retrieveAllElements().count(), 2)
    }

    companion object {
        @JvmStatic
        @BeforeAll
        fun build() {
            DiskHistoryCache.cacheDirectory = "${System.getProperty("user.home")}${File.separator}dungeon-hub${File.separator}hypixel-wrapper-test"
        }

        @JvmStatic
        @AfterAll
        fun tearDown() {
            DiskCacheApiClient.clearCache()
        }
    }
}