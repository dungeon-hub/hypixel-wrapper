package net.dungeonhub.hypixel.client

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import net.dungeonhub.hypixel.connection.HypixelConnection
import net.dungeonhub.hypixel.entities.guild.Guild
import net.dungeonhub.hypixel.entities.guild.toGuild
import net.dungeonhub.hypixel.entities.player.HypixelPlayer
import net.dungeonhub.hypixel.entities.player.toHypixelPlayer
import net.dungeonhub.hypixel.entities.skyblock.SkyblockProfiles
import net.dungeonhub.hypixel.entities.skyblock.toSkyblockProfile
import net.dungeonhub.provider.GsonProvider
import net.dungeonhub.provider.getAsJsonObjectOrNull
import okhttp3.HttpUrl.Companion.toHttpUrl
import java.util.*

object RestApiClient : ApiClient {
    const val API_PREFIX = "https://api.hypixel.net/v2/"

    override fun getPlayerData(uuid: UUID): HypixelPlayer? {
        val player = HypixelConnection.hypixelApi.getPlayerByUuid(uuid).join().player

        if (player?.uuid == null) {
            return null
        }

        return player.raw.toHypixelPlayer()
    }

    override fun getSkyblockProfiles(uuid: UUID): SkyblockProfiles {
        return SkyblockProfiles(
            uuid,
            fetchSkyblockProfiles(uuid)?.asList()?.map {
                it.toSkyblockProfile()
            } ?: emptyList()
        )
    }

    override fun getGuild(name: String): Guild? {
        val url = (API_PREFIX + "guild").toHttpUrl().newBuilder().addEncodedQueryParameter("name", name).build()

        val response = HypixelConnection.makeAuthenticatedRequest(url.toString()).join()

        if (response.statusCode != 200 || response.body.isNullOrBlank()) {
            return null
        }

        val jsonObject = GsonProvider.gson.fromJson(response.body, JsonObject::class.java)

        return jsonObject.getAsJsonObjectOrNull("guild")?.toGuild()
    }

    fun fetchSkyblockProfiles(uuid: UUID): JsonArray? {
        return HypixelConnection.hypixelApi.getSkyBlockProfiles(uuid).join().profiles
    }
}