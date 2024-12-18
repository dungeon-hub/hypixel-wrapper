package net.dungeonhub

import com.google.gson.Gson
import com.google.gson.JsonObject
import net.dungeonhub.client.MemoryCacheApiClient
import net.dungeonhub.connection.HypixelApiConnection
import net.dungeonhub.provider.CacheApiClientProvider
import net.dungeonhub.strategy.ApiClientStrategy
import net.hypixel.api.reply.PlayerReply.Player
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class TestPlayerData {
    @Test
    fun testPlayerDataCache() {
        val rawData = listOf(
            UUID.fromString("1686c45d-f082-4811-b1c8-b1db7810e255") to "{\"uuid\": \"1686c45df0824811b1c8b1db7810e255\",\"playername\": \"sirdowntime\",\"displayname\": \"SirDowntime\",\"socialMedia\": {\"links\": {\"DISCORD\": \"sirdowntime\"},\"prompt\": true}}",
            UUID.fromString("39642ffc-a7fb-4d24-a1d4-916f4cad1d98") to "{\"uuid\": \"39642ffca7fb4d24a1d4916f4cad1d98\",\"playername\": \"taubsie\",\"displayname\": \"Taubsie\",\"socialMedia\": {\"links\": {\"DISCORD\": \"taubsie\"},\"prompt\": true}}"
        )

        val links = listOf(
            UUID.fromString("1686c45d-f082-4811-b1c8-b1db7810e255") to "sirdowntime",
            UUID.fromString("39642ffc-a7fb-4d24-a1d4-916f4cad1d98") to "taubsie"
        )

        val connection = HypixelApiConnection(ApiClientStrategy.Cache)

        //Check if cache is empty
        for (pair in rawData) {
            assertNull(connection.getPlayerData(pair.first))
        }

        for(pair in links) {
            assertNull(connection.getHypixelLinkedDiscord(pair.first))
        }

        assertEquals((connection.strategy.client as MemoryCacheApiClient).playerDataCache.retrieveAllElements().count(), 0)

        //Store example data in cache
        for (pair in rawData) {
            val player = Gson().fromJson(pair.second, JsonObject::class.java)

            CacheApiClientProvider.client.playerDataCache.store(Player(player))
        }

        //Check if cache is filled
        for (pair in rawData) {
            assertNotNull(connection.getPlayerData(pair.first))
        }

        for(pair in links) {
            assertEquals(connection.getHypixelLinkedDiscord(pair.first), pair.second)
        }

        assertEquals((connection.strategy.client as MemoryCacheApiClient).playerDataCache.retrieveAllElements().count(), 2)
    }
}