package net.dungeonhub.hypixel.client

import com.google.gson.reflect.TypeToken
import net.dungeonhub.cache.Cache
import net.dungeonhub.cache.CacheType
import net.dungeonhub.cache.disk.DiskHistoryCache
import net.dungeonhub.cache.memory.CacheElement
import net.dungeonhub.cache.memory.HashMapCache
import net.dungeonhub.hypixel.entities.bingo.SkyblockBingoData
import net.dungeonhub.hypixel.entities.guild.Guild
import net.dungeonhub.hypixel.entities.player.HypixelPlayer
import net.dungeonhub.hypixel.entities.skyblock.SkyblockProfiles
import java.util.*

class CacheApiClient : ApiClientWithCache {
    override val playerDataCache = buildCache(
        CachedResource.PlayerData,
        object : TypeToken<CacheElement<HypixelPlayer>>() {},
        { it.uuid },
        { UUID.fromString(it) }
    )
    override val skyblockProfilesCache = buildCache(
        CachedResource.SkyblockProfiles,
        object : TypeToken<CacheElement<SkyblockProfiles>>() {},
        { it.owner },
        { UUID.fromString(it) }
    )
    override val guildCache = buildCache(
        CachedResource.Guilds,
        object : TypeToken<CacheElement<Guild>>() {},
        { it.name },
        { it }
    )
    override val bingoDataCache = buildCache(
        CachedResource.BingoData,
        object : TypeToken<CacheElement<SkyblockBingoData>>() {},
        { it.player },
        { UUID.fromString(it) }
    )

    companion object {
        fun <T, K> buildCache(
            resourceType: CachedResource,
            typeToken: TypeToken<CacheElement<T>>,
            keyFunction: (T) -> K,
            keyParser: (String) -> K?
        ): Cache<T, K> {
            val cacheTypes = resourceType.cacheTypes
            val cacheType = cacheTypes.firstOrNull { it.active } ?: CacheType.Memory // TODO should this rather be a no-op cache then? :P

            return when (cacheType) {
                CacheType.Memory -> HashMapCache(
                    keyFunction
                )
                CacheType.Disk -> DiskHistoryCache(
                    resourceType.resourceName,
                    typeToken,
                    keyFunction,
                    keyParser
                )
                CacheType.Database -> TODO()
                CacheType.Redis -> TODO()
            }
        }
    }
}