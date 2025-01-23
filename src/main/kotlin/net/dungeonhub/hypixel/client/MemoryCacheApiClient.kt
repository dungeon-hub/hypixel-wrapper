package net.dungeonhub.hypixel.client

import net.dungeonhub.cache.memory.HashMapCache
import net.dungeonhub.hypixel.entities.skyblock.SkyblockProfiles
import net.dungeonhub.hypixel.entities.player.HypixelPlayer
import java.util.*

object MemoryCacheApiClient : ApiClientWithCache {
    override val playerDataCache = HashMapCache<HypixelPlayer, UUID> { it.uuid }
    override val skyblockProfilesCache = HashMapCache<SkyblockProfiles, UUID> { it.owner }
}