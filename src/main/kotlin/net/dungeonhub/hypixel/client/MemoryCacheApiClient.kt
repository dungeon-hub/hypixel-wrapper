package net.dungeonhub.hypixel.client

import net.dungeonhub.cache.memory.HashMapCache
import net.dungeonhub.hypixel.entities.SkyblockProfiles
import net.hypixel.api.reply.PlayerReply
import java.util.*

object MemoryCacheApiClient : ApiClientWithCache {
    override val playerDataCache = HashMapCache<PlayerReply.Player, UUID> { it.uuid }
    override val skyblockProfilesCache = HashMapCache<SkyblockProfiles, UUID> { it.owner }
}