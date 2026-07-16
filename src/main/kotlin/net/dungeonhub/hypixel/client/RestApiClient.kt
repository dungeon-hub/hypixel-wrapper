package net.dungeonhub.hypixel.client

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import net.dungeonhub.hypixel.client.resources.ResourceApiClient
import net.dungeonhub.hypixel.connection.HypixelConnection
import net.dungeonhub.hypixel.entities.bingo.CurrentBingoEvent
import net.dungeonhub.hypixel.entities.bingo.SkyblockBingoData
import net.dungeonhub.hypixel.entities.bingo.toCurrentBingoEvent
import net.dungeonhub.hypixel.entities.bingo.toSkyblockBingoData
import net.dungeonhub.hypixel.entities.guild.Guild
import net.dungeonhub.hypixel.entities.guild.toGuild
import net.dungeonhub.hypixel.entities.player.HypixelPlayer
import net.dungeonhub.hypixel.entities.player.toHypixelPlayer
import net.dungeonhub.hypixel.entities.skyblock.SkyblockProfiles
import net.dungeonhub.hypixel.entities.skyblock.toSkyblockProfile
import net.dungeonhub.hypixel.entities.status.PlayerSession
import net.dungeonhub.hypixel.entities.status.toPlayerSession
import net.dungeonhub.provider.GsonProvider
import net.dungeonhub.provider.getAsJsonObjectOrNull
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.slf4j.LoggerFactory
import java.util.*
import java.util.concurrent.CompletionException

object RestApiClient : ApiClient, ResourceApiClient {
    const val API_PREFIX = "https://api.hypixel.net/v2/"

    private val logger = LoggerFactory.getLogger(RestApiClient::class.java)

    override fun getPlayerData(uuid: UUID): HypixelPlayer? {
        return try {
            val player = HypixelConnection.hypixelApi.getPlayerByUuid(uuid).join().player
            if (player?.uuid == null) null else player.raw.toHypixelPlayer()
        } catch (e: CompletionException) {
            logger.warn("Failed to fetch player data for {}: {}", uuid, e.cause?.message ?: e.message)
            null
        }
    }

    override fun getSession(uuid: UUID): PlayerSession? {
        val url = (API_PREFIX + "status").toHttpUrl().newBuilder().addEncodedQueryParameter("uuid", uuid.toString()).build()
        return makeAuthenticatedRequest(url.toString()) { json ->
            json.getAsJsonObjectOrNull("session")?.toPlayerSession(uuid)
        }
    }

    override fun getSkyblockProfiles(uuid: UUID): SkyblockProfiles {
        return SkyblockProfiles(
            uuid,
            fetchSkyblockProfiles(uuid)?.asList()?.map {
                it.toSkyblockProfile()
            } ?: emptyList()
        )
    }

    fun fetchSkyblockProfiles(uuid: UUID): JsonArray? {
        return try {
            HypixelConnection.hypixelApi.getSkyBlockProfiles(uuid).join().profiles
        } catch (e: CompletionException) {
            logger.warn("Failed to fetch SkyBlock profiles for {}: {}", uuid, e.cause?.message ?: e.message)
            null
        }
    }

    override fun getGuild(name: String): Guild? {
        val url = (API_PREFIX + "guild").toHttpUrl().newBuilder().addEncodedQueryParameter("name", name).build()
        return makeAuthenticatedRequest(url.toString()) { json ->
            json.getAsJsonObjectOrNull("guild")?.toGuild()
        }
    }

    override fun getPlayerGuild(uuid: UUID): Guild? {
        val url = (API_PREFIX + "guild").toHttpUrl().newBuilder()
            .addEncodedQueryParameter("player", uuid.toString()).build()
        return makeAuthenticatedRequest(url.toString()) { json ->
            json.getAsJsonObjectOrNull("guild")?.toGuild(uuid)
        }
    }

    override fun getBingoData(uuid: UUID): SkyblockBingoData? {
        val url = (API_PREFIX + "skyblock/bingo").toHttpUrl().newBuilder()
            .addEncodedQueryParameter("uuid", uuid.toString()).build()
        return makeAuthenticatedRequest(url.toString()) { json ->
            json.toSkyblockBingoData(uuid)
        }
    }

    override fun getCurrentBingoEvent(): CurrentBingoEvent? {
        val url = (API_PREFIX + "resources/skyblock/bingo").toHttpUrl()
        return try {
            val response = HypixelConnection.makeRequest(url.toString()).join()
            if (response.statusCode != 200 || response.body.isNullOrBlank()) return null
            val jsonElement = GsonProvider.gson.fromJson(response.body, JsonElement::class.java)
            if (!jsonElement.isJsonObject) return null
            jsonElement.asJsonObject.toCurrentBingoEvent()
        } catch (e: CompletionException) {
            logger.warn("Failed to fetch current bingo event: {}", e.cause?.message ?: e.message)
            null
        }
    }

    private fun <T> makeAuthenticatedRequest(url: String, parse: (com.google.gson.JsonObject) -> T?): T? {
        return try {
            val response = HypixelConnection.makeAuthenticatedRequest(url).join()
            if (response.statusCode != 200 || response.body.isNullOrBlank()) return null
            val jsonElement = GsonProvider.gson.fromJson(response.body, JsonElement::class.java)
            if (!jsonElement.isJsonObject) return null
            parse(jsonElement.asJsonObject)
        } catch (e: CompletionException) {
            logger.warn("Failed to perform authenticated request to {}: {}", url, e.cause?.message ?: e.message)
            null
        }
    }
}