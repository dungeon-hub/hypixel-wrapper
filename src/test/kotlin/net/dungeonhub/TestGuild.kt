package net.dungeonhub

import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkAll
import net.dungeonhub.hypixel.client.RestApiClient
import net.dungeonhub.hypixel.connection.HypixelConnection
import net.dungeonhub.hypixel.entities.guild.GuildMasterRank
import net.dungeonhub.service.TestHelper
import net.hypixel.api.http.HypixelHttpResponse
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.assertDoesNotThrow
import java.util.concurrent.CompletableFuture
import kotlin.test.*

class TestGuild {
    @Test
    fun testGuildRequestParsing() {
        assertNotNull(RestApiClient.getGuild("Freiheit"))
        assertNotNull(RestApiClient.getGuild("Dungeon Hub"))
        assertNotNull(RestApiClient.getGuild("DungeonHub"))

        assertNull(RestApiClient.getGuild("unknown guild"))
        assertNull(RestApiClient.getGuild("abcdefghijklmnopqrstuvwxyz"))
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

            assertNotNull(guild.ranks.firstOrNull { it.name == GuildMasterRank.GUILD_MASTER_NAME })
            assertEquals(guild.creationDate, guild.ranks.first { it.name == GuildMasterRank.GUILD_MASTER_NAME }.created)

            guild.ranks.forEach {
                assertEquals(it.name == GuildMasterRank.GUILD_MASTER_NAME, it.owner)
                assertEquals(it is GuildMasterRank, it.owner)
            }
        }
    }

    @Test
    fun testCorrectRankAssignment() {
        for (guild in TestHelper.readAllGuilds()) {
            for (member in guild.members) {
                assertNotNull(member.rank, "Member ${member.uuid} of guild ${guild.displayName} doesn't have a rank!")
            }
        }
    }

    @Test
    fun testExperienceHistoryParsing() {
        for (guild in TestHelper.readAllGuilds()) {
            for (member in guild.members) {
                assertDoesNotThrow { member.experienceHistory }
                member.experienceHistory.keys.forEach {
                    assertNotNull(it)
                }
            }
        }
    }

    companion object {
        @BeforeAll
        @JvmStatic
        fun setupHypixelApi() {
            mockkObject(HypixelConnection)

            every { HypixelConnection.makeAuthenticatedRequest(any()) }.answers {
                val url = it.invocation.args[0] as String

                if (!url.startsWith("https://api.hypixel.net/v2/guild?name=")) {
                    return@answers CompletableFuture.completedFuture(HypixelHttpResponse(404, null, null))
                }

                val guildName = url.toHttpUrl().queryParameter("name")

                try {
                    val json = "{\"success\": true, \"guild\": " + TestHelper.readFile("guilds/$guildName.json") + "}"

                    return@answers CompletableFuture.completedFuture(HypixelHttpResponse(200, json, null))
                } catch (_: NullPointerException) {
                    return@answers CompletableFuture.completedFuture(HypixelHttpResponse(404, null, null))
                }
            }
        }

        @AfterAll
        @JvmStatic
        fun teardownHypixelApi() {
            unmockkAll()
        }
    }
}