package net.dungeonhub.client

import net.dungeonhub.connection.HypixelConnection
import net.hypixel.api.reply.PlayerReply
import java.util.*

object RestApiClient : ApiClient {
    override fun getPlayerData(uuid: UUID): PlayerReply.Player? {
        return HypixelConnection.hypixelApi.getPlayerByUuid(uuid).join().player
    }
}