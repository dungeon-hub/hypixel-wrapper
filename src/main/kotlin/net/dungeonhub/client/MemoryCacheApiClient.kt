package net.dungeonhub.client

import net.dungeonhub.cache.memory.HashMapCache
import net.dungeonhub.entities.SkyblockProfiles
import net.hypixel.api.reply.PlayerReply
import java.util.*

object MemoryCacheApiClient : ApiClientWithCache {
    override val playerDataCache = HashMapCache<PlayerReply.Player, UUID> { it.uuid }
    override val skyblockProfilesCache = HashMapCache<SkyblockProfiles, UUID> { it.owner }


    override fun getPlayerData(uuid: UUID): PlayerReply.Player? {
        return playerDataCache.retrieve(uuid)
    }

    override fun getSkyblockProfiles(uuid: UUID): SkyblockProfiles? {
        return skyblockProfilesCache.retrieve(uuid)
    }
}