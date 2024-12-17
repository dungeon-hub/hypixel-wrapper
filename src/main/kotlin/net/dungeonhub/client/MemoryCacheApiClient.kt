package net.dungeonhub.client

import net.dungeonhub.cache.memory.HashMapCache
import net.hypixel.api.reply.PlayerReply
import java.util.*

object MemoryCacheApiClient : ApiClientWithCache {
    override val playerDataCache = HashMapCache<PlayerReply.Player, UUID> { it.uuid }

    override fun getPlayerData(uuid: UUID): PlayerReply.Player? {
        return playerDataCache.retrieve(uuid)
    }
}