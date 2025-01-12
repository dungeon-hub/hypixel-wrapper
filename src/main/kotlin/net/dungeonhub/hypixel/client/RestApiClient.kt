package net.dungeonhub.hypixel.client

import com.google.gson.JsonArray
import net.dungeonhub.hypixel.connection.HypixelConnection
import net.dungeonhub.hypixel.entities.SkyblockProfiles
import net.dungeonhub.hypixel.entities.player.HypixelPlayer
import net.dungeonhub.hypixel.entities.player.toHypixelPlayer
import net.dungeonhub.hypixel.entities.toSkyblockProfile
import java.util.*

//TODO add test(s) for what happens when endpoints return nothing / are down
object RestApiClient : ApiClient {
    override fun getPlayerData(uuid: UUID): HypixelPlayer? {
        val player = HypixelConnection.hypixelApi.getPlayerByUuid(uuid).join().player

        if(player?.uuid == null) {
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

    fun fetchSkyblockProfiles(uuid: UUID): JsonArray? {
        return HypixelConnection.hypixelApi.getSkyBlockProfiles(uuid).join().profiles
    }
}