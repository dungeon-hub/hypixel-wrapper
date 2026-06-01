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
import java.util.*

object RestApiClient : ApiClient, ResourceApiClient {
    const val API_PREFIX = "https://api.hypixel.net/v2/"

    override fun getPlayerData(uuid: UUID): HypixelPlayer? {
        val player = HypixelConnection.hypixelApi.getPlayerByUuid(uuid).join().player

        if (player?.uuid == null) {
            return null
        }

        return player.raw.toHypixelPlayer()
    }

    override fun getSession(uuid: UUID): PlayerSession? {
        val url = (API_PREFIX + "status").toHttpUrl().newBuilder().addEncodedQueryParameter("uuid", uuid.toString()).build()

        val response = HypixelConnection.makeAuthenticatedRequest(url.toString()).join()

        if (response.statusCode != 200 || response.body.isNullOrBlank()) {
            return null
        }

        val jsonElement = GsonProvider.gson.fromJson(response.body, JsonElement::class.java)

        if (!jsonElement.isJsonObject) {
            return null
        }

        return jsonElement.asJsonObject.getAsJsonObjectOrNull("session")?.toPlayerSession(uuid)
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
        return HypixelConnection.hypixelApi.getSkyBlockProfiles(uuid).join().profiles
    }

    override fun getGuild(name: String): Guild? {
        val url = (API_PREFIX + "guild").toHttpUrl().newBuilder().addEncodedQueryParameter("name", name).build()

        val response = HypixelConnection.makeAuthenticatedRequest(url.toString()).join()

        if (response.statusCode != 200 || response.body.isNullOrBlank()) {
            return null
        }

        val jsonElement = GsonProvider.gson.fromJson(response.body, JsonElement::class.java)

        if (!jsonElement.isJsonObject) {
            return null
        }

        return jsonElement.asJsonObject.getAsJsonObjectOrNull("guild")?.toGuild()
    }

    override fun getPlayerGuild(uuid: UUID): Guild? {
        val url = (API_PREFIX + "guild").toHttpUrl().newBuilder()
            .addEncodedQueryParameter("player", uuid.toString()).build()

        val response = HypixelConnection.makeAuthenticatedRequest(url.toString()).join()

        if (response.statusCode != 200 || response.body.isNullOrBlank()) {
            return null
        }

        val jsonElement = GsonProvider.gson.fromJson(response.body, JsonElement::class.java)

        if (!jsonElement.isJsonObject) {
            return null
        }

        return jsonElement.asJsonObject.getAsJsonObjectOrNull("guild")?.toGuild(uuid)
    }

    override fun getBingoData(uuid: UUID): SkyblockBingoData? {
        val url = (API_PREFIX + "skyblock/bingo").toHttpUrl().newBuilder()
            .addEncodedQueryParameter("uuid", uuid.toString()).build()

        val response = HypixelConnection.makeAuthenticatedRequest(url.toString()).join()

        if (response.statusCode != 200 || response.body.isNullOrBlank()) {
            return null
        }

        val jsonElement = GsonProvider.gson.fromJson(response.body, JsonElement::class.java)

        if (!jsonElement.isJsonObject) {
            return null
        }

        return jsonElement.asJsonObject.toSkyblockBingoData(uuid)
    }

    override fun getCurrentBingoEvent(): CurrentBingoEvent? {
        val url = (API_PREFIX + "resources/skyblock/bingo").toHttpUrl()

        val response = HypixelConnection.makeRequest(url.toString()).join()

        if (response.statusCode != 200 || response.body.isNullOrBlank()) {
            return null
        }

        val jsonElement = GsonProvider.gson.fromJson(response.body, JsonElement::class.java)

        if (!jsonElement.isJsonObject) {
            return null
        }

        return jsonElement.asJsonObject.toCurrentBingoEvent()
    }
}