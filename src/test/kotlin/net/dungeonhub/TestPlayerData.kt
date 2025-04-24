package net.dungeonhub

import com.google.gson.JsonObject
import net.dungeonhub.hypixel.client.MemoryCacheApiClient
import net.dungeonhub.hypixel.connection.HypixelApiConnection
import net.dungeonhub.hypixel.entities.player.KnownRank
import net.dungeonhub.hypixel.entities.player.KnownSocialMediaType
import net.dungeonhub.hypixel.entities.player.Rank
import net.dungeonhub.hypixel.entities.player.toHypixelPlayer
import net.dungeonhub.hypixel.provider.CacheApiClientProvider
import net.dungeonhub.provider.GsonProvider
import net.dungeonhub.service.TestHelper
import net.dungeonhub.strategy.ApiClientStrategy
import java.util.*
import kotlin.test.*

class TestPlayerData {
    @Test
    fun testPlayerDataCache() {
        val rawData = listOf(
            UUID.fromString("1686c45d-f082-4811-b1c8-b1db7810e255") to TestHelper.readFile("player-data/1686c45d-f082-4811-b1c8-b1db7810e255.json"),
            UUID.fromString("39642ffc-a7fb-4d24-a1d4-916f4cad1d98") to TestHelper.readFile("player-data/39642ffc-a7fb-4d24-a1d4-916f4cad1d98.json")
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

        for (pair in links) {
            assertNull(connection.getHypixelLinkedDiscord(pair.first))
        }

        assertEquals(
            (connection.strategy.client as MemoryCacheApiClient).playerDataCache.retrieveAllElements().count(),
            0
        )

        //Store example data in cache
        for (pair in rawData) {
            val player = GsonProvider.gson.fromJson(pair.second, JsonObject::class.java)

            CacheApiClientProvider.client.playerDataCache.store(player.toHypixelPlayer())
        }

        //Check if cache is filled
        for (pair in rawData) {
            assertNotNull(connection.getPlayerData(pair.first))
        }

        for (pair in links) {
            assertEquals(connection.getHypixelLinkedDiscord(pair.first), pair.second)
        }

        assertEquals(
            (connection.strategy.client as MemoryCacheApiClient).playerDataCache.retrieveAllElements().count(),
            2
        )
    }

    @Test
    fun testHypixelPlayerParsing() {
        for (hypixelPlayer in TestHelper.readAllHypixelPlayers()) {
            assertNotNull(hypixelPlayer.uuid)
        }
    }

    @Test
    fun testNoUnknownDataTypes() {
        for (hypixelPlayer in TestHelper.readAllHypixelPlayers()) {
            hypixelPlayer.socialMediaLinks.keys.forEach {
                assertIsNot<KnownSocialMediaType.UnknownSocialMediaType>(it)
            }

            assertIsNot<KnownRank.UnknownRank>(hypixelPlayer.rank)
        }
    }

    @Test
    fun testCorrectRanks() {
        val ranks = mapOf<UUID, Rank>(
            UUID.fromString("1e0f27c7-5385-40f3-b4d5-707a91726249") to KnownRank.Default,
            UUID.fromString("069a79f4-44e9-4726-a5be-fca90e38aaf5") to KnownRank.Default,
            UUID.fromString("368ce085-0749-432f-9ab8-6bebd6409ea0") to KnownRank.Default,
            UUID.fromString("f5aa9582-6862-4b94-a2cb-9ee288ee0f9a") to KnownRank.Default,

            UUID.fromString("b1109faf-7225-4f0b-8e42-e4d1ed3bdada") to KnownRank.Vip,

            UUID.fromString("1686c45d-f082-4811-b1c8-b1db7810e255") to KnownRank.VipPlus,

            UUID.fromString("6d445e94-f3ae-4bc8-aac9-bf5bd4d76337") to KnownRank.Mvp,

            UUID.fromString("12e8d4cc-1ab1-4290-938b-cf978bee1972") to KnownRank.MvpPlus,
            UUID.fromString("39642ffc-a7fb-4d24-a1d4-916f4cad1d98") to KnownRank.MvpPlus,
            UUID.fromString("e4704ec0-63cc-435f-9971-1b20577617dc") to KnownRank.MvpPlus,
            UUID.fromString("91821440-2b71-4cdb-8364-611c3e435e4b") to KnownRank.MvpPlus,
            UUID.fromString("39659d02-f0b4-4d39-99f2-eb3566fad1f1") to KnownRank.MvpPlus,
            UUID.fromString("1a5130bf-2ac5-44ca-a6c6-b1f8afd008bf") to KnownRank.MvpPlus,

            UUID.fromString("92d940ad-10e8-4d9d-a795-adcf5fd6b0c6") to KnownRank.MvpPlusPlus,

            UUID.fromString("312bd230-8cb0-4ec7-9bb4-6cbc1c5ee3e3") to KnownRank.YouTube,
            UUID.fromString("b876ec32-e396-476b-a115-8438d83c67d4") to KnownRank.YouTube,
            UUID.fromString("ec70bcaf-702f-4bb8-b48d-276fa52a780c") to KnownRank.YouTube,

            UUID.fromString("16751f79-c0b1-4e53-a0b5-90d31fc1d80d") to KnownRank.Staff,
            UUID.fromString("9b2a30ec-f8b3-4dfe-bf49-9c5c367383f8") to KnownRank.Staff,
            UUID.fromString("bcd2033c-63ec-4bf8-8aca-680b22461340") to KnownRank.Staff,
            UUID.fromString("c31f8346-78a8-403b-9006-0b69f57f7626") to KnownRank.Staff,
            UUID.fromString("f58debd5-9f50-4222-8f60-22211d4c140c") to KnownRank.Staff,
            UUID.fromString("f7c77d99-9f15-4a66-a87d-c4a51ef30d19") to KnownRank.Staff
        )

        for (hypixelPlayer in TestHelper.readAllHypixelPlayers()) {
            assertEquals(
                ranks[hypixelPlayer.uuid],
                hypixelPlayer.rank,
                "Incorrect rank for player ${hypixelPlayer.displayName} (${hypixelPlayer.uuid})"
            )
        }
    }
}