package net.dungeonhub.client

import net.dungeonhub.cache.Cache
import net.hypixel.api.reply.PlayerReply
import java.util.*

object DatabaseCacheApiClient : ApiClientWithCache {
    override val playerDataCache: Cache<PlayerReply.Player, UUID>
        get() = TODO("Not yet implemented")

    override fun getPlayerData(uuid: UUID): PlayerReply.Player? {
        TODO("Not yet implemented")
    }
}