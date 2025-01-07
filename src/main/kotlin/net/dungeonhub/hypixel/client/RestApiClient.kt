package net.dungeonhub.hypixel.client

import net.dungeonhub.hypixel.connection.HypixelConnection
import net.dungeonhub.hypixel.entities.SkyblockProfiles
import net.dungeonhub.hypixel.entities.toSkyblockProfile
import net.hypixel.api.reply.PlayerReply
import java.util.*

//TODO add test(s) for what happens when endpoints return nothing / are down
object RestApiClient : ApiClient {
    override fun getPlayerData(uuid: UUID): PlayerReply.Player? {
        val player = HypixelConnection.hypixelApi.getPlayerByUuid(uuid).join().player

        if(player?.uuid == null) {
            return null
        }

        return player
    }

    override fun getSkyblockProfiles(uuid: UUID): SkyblockProfiles {
        return SkyblockProfiles(
            uuid,
            HypixelConnection.hypixelApi.getSkyBlockProfiles(uuid).join().profiles?.asList()?.map {
                it.toSkyblockProfile()
            } ?: emptyList()
        )
    }
}