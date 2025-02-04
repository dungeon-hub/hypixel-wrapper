package net.dungeonhub

import net.dungeonhub.hypixel.client.RestApiClient
import net.dungeonhub.service.TestHelper
import net.hypixel.api.http.HypixelHttpResponse
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.junit.jupiter.api.assertDoesNotThrow
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class TestGuild {
    @Test
    fun testGuildRequestParsing() {
        assertNotNull(restApiClient.getGuild("Freiheit"))
        assertNotNull(restApiClient.getGuild("Dungeon Hub"))
        assertNotNull(restApiClient.getGuild("DungeonHub"))

        assertNull(restApiClient.getGuild("unknown guild"))
        assertNull(restApiClient.getGuild("abcdefghijklmnopqrstuvwxyz"))
    }

    @Test
    fun testCorrectGuildParsing() {
        assertDoesNotThrow {
            TestHelper.readAllGuilds()
        }
    }

    @Test
    fun testCorrectRankPriority() {
        for (guild in TestHelper.readAllGuilds()) {
            assertTrue(
                guild.ranks == guild.ranks.sortedBy { it.priority },
                "The guild ranks for ${guild.displayName} weren't automatically sorted!"
            )

            //2 different ways to check if all values in the list are present
            for ((index, rank) in guild.ranks.withIndex()) {
                assertTrue(index + 1 == rank.priority)
            }
            assertTrue(
                (1..guild.ranks.size).toList() == guild.ranks.map { it.priority },
                "The rank priority is not in order for guild ${guild.name}: ${guild.ranks.map { it.priority }}"
            )
        }
    }

    companion object {
        private val restApiClient: RestApiClient = mock<RestApiClient> {
            on { makeAuthenticatedHypixelRequest(any()) }.thenAnswer {
                val url = it.getArgument<String>(0)

                if (!url.startsWith("https://api.hypixel.net/v2/guild?name=")) {
                    return@thenAnswer HypixelHttpResponse(404, null, null)
                }

                val guildName = url.toHttpUrl().queryParameter("name")

                try {
                    val json = "{\"success\": true, \"guild\": " + TestHelper.readFile("guilds/$guildName.json") + "}"

                    return@thenAnswer HypixelHttpResponse(200, json, null)
                } catch (_: NullPointerException) {
                    return@thenAnswer HypixelHttpResponse(404, null, null)
                }
            }
            on { getGuild(any()) }.thenCallRealMethod()
        }
    }
}