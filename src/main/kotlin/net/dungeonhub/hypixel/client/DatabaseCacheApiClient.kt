package net.dungeonhub.hypixel.client

import com.google.gson.reflect.TypeToken
import net.dungeonhub.cache.Cache
import net.dungeonhub.cache.database.MongoCache
import net.dungeonhub.cache.database.MongoCacheProvider
import net.dungeonhub.cache.memory.CacheElement
import net.dungeonhub.hypixel.client.CachedResource.*
import net.dungeonhub.hypixel.entities.bingo.SkyblockBingoData
import net.dungeonhub.hypixel.entities.guild.Guild
import net.dungeonhub.hypixel.entities.player.HypixelPlayer
import net.dungeonhub.hypixel.entities.skyblock.SkyblockProfiles
import java.util.*

object DatabaseCacheApiClient : ApiClientWithCache {
    override val playerDataCache: Cache<HypixelPlayer, UUID> = MongoCache(
        MongoCacheProvider.getCollection(PlayerData.resourceName),
        object : TypeToken<CacheElement<HypixelPlayer>>() {},
        { it.uuid }
    )

    override val skyblockProfilesCache: Cache<SkyblockProfiles, UUID> = MongoCache(
        MongoCacheProvider.getCollection(SkyblockProfiles.resourceName),
        object : TypeToken<CacheElement<SkyblockProfiles>>() {},
        { it.owner }
    )

    override val guildCache: Cache<Guild, String> = MongoCache(
        MongoCacheProvider.getCollection(Guilds.resourceName),
        object : TypeToken<CacheElement<Guild>>() {},
        { it.name }
    )

    override val bingoDataCache: Cache<SkyblockBingoData, UUID> = MongoCache(
        MongoCacheProvider.getCollection(BingoData.resourceName),
        object : TypeToken<CacheElement<SkyblockBingoData>>() {},
        { it.player }
    )
}
