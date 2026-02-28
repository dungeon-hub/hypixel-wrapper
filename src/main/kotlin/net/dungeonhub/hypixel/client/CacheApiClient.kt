package net.dungeonhub.hypixel.client

import com.google.gson.reflect.TypeToken
import net.dungeonhub.cache.Cache
import net.dungeonhub.cache.CacheType
import net.dungeonhub.cache.database.MongoCache
import net.dungeonhub.cache.database.MongoCacheProvider
import net.dungeonhub.cache.disk.DiskHistoryCache
import net.dungeonhub.cache.memory.CacheElement
import net.dungeonhub.cache.memory.HashMapCache
import net.dungeonhub.hypixel.entities.bingo.SkyblockBingoData
import net.dungeonhub.hypixel.entities.guild.Guild
import net.dungeonhub.hypixel.entities.player.HypixelPlayer
import net.dungeonhub.hypixel.entities.skyblock.SkyblockProfiles
import net.dungeonhub.hypixel.entities.status.PlayerSession
import net.dungeonhub.hypixel.provider.CacheApiClientProvider
import java.util.*

class CacheApiClient(cacheType: CacheType? = null) : ApiClientWithCache {
    override val playerDataCache = buildCache(
        CachedResource.PlayerData,
        object : TypeToken<CacheElement<HypixelPlayer>>() {},
        { it.uuid },
        { UUID.fromString(it) },
        cacheType
    )
    override val sessionCache = buildCache(
        CachedResource.PlayerSession,
        object : TypeToken<CacheElement<PlayerSession>>() {},
        { it.uuid },
        { UUID.fromString(it) },
        cacheType
    )
    override val skyblockProfilesCache = buildCache(
        CachedResource.SkyblockProfiles,
        object : TypeToken<CacheElement<SkyblockProfiles>>() {},
        { it.owner },
        { UUID.fromString(it) },
        cacheType
    )
    override val guildCache = buildCache(
        CachedResource.Guilds,
        object : TypeToken<CacheElement<Guild>>() {},
        { it.name },
        { it },
        cacheType
    )
    override val playerGuildCache = buildCache(
        CachedResource.PlayerGuilds,
        object : TypeToken<CacheElement<Guild>>() {},
        { it.playerUuid!! },
        { UUID.fromString(it) },
        cacheType
    )
    override val bingoDataCache = buildCache(
        CachedResource.BingoData,
        object : TypeToken<CacheElement<SkyblockBingoData>>() {},
        { it.player },
        { UUID.fromString(it) },
        cacheType
    )

    companion object {
        fun <T, K> buildCache(
            resourceType: CachedResource,
            typeToken: TypeToken<CacheElement<T>>,
            keyFunction: (T) -> K,
            keyParser: (String) -> K?,
            selectedCacheType: CacheType?
        ): Cache<T, K> {
            val cacheType = (selectedCacheType ?: CacheApiClientProvider.cacheType)?.takeIf { it.active }
                ?: CacheType.Memory

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
                CacheType.Database -> MongoCache(
                    MongoCacheProvider.getCollection(resourceType.resourceName),
                    typeToken,
                    keyFunction
                )
            }
        }
    }
}