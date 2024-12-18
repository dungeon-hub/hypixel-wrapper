package net.dungeonhub.client

import net.dungeonhub.entities.SkyblockProfiles
import net.hypixel.api.reply.PlayerReply
import java.util.*

class FallbackApiClient(val first: ApiClient, val second: ApiClient) : ApiClient {
    override fun getPlayerData(uuid: UUID): PlayerReply.Player? = first.getPlayerData(uuid) ?: second.getPlayerData(uuid)
    override fun getSkyblockProfiles(uuid: UUID): SkyblockProfiles? = first.getSkyblockProfiles(uuid) ?: second.getSkyblockProfiles(uuid)
}