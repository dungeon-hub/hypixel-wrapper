package net.dungeonhub.client

import net.dungeonhub.connection.HypixelConnection
import net.dungeonhub.entities.SkyblockProfiles
import net.dungeonhub.entities.toSkyblockProfile
import net.hypixel.api.reply.PlayerReply
import java.util.*

object RestApiClient : ApiClient {
    override fun getPlayerData(uuid: UUID): PlayerReply.Player? {
        return HypixelConnection.hypixelApi.getPlayerByUuid(uuid).join().player
    }

    override fun getSkyblockProfiles(uuid: UUID): SkyblockProfiles {
        return SkyblockProfiles(
            uuid,
            HypixelConnection.hypixelApi.getSkyBlockProfiles(uuid).join().profiles.asList().map {
                it.toSkyblockProfile()
            }
        )
    }
}