package net.dungeonhub.client

import net.hypixel.api.reply.PlayerReply
import java.util.*

class FallbackApiClient(val first: ApiClient, val second: ApiClient) : ApiClient {
    override fun getPlayerData(uuid: UUID): PlayerReply.Player? = first.getPlayerData(uuid) ?: second.getPlayerData(uuid)
}