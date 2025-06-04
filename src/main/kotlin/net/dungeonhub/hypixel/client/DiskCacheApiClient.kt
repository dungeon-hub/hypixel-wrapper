package net.dungeonhub.hypixel.client

import com.google.gson.reflect.TypeToken
import net.dungeonhub.cache.disk.DiskHistoryCache
import net.dungeonhub.cache.memory.CacheElement
import net.dungeonhub.hypixel.entities.bingo.SkyblockBingoData
import net.dungeonhub.hypixel.entities.guild.Guild
import net.dungeonhub.hypixel.entities.player.HypixelPlayer
import net.dungeonhub.hypixel.entities.skyblock.SkyblockProfiles
import java.nio.file.Path
import java.util.*
import kotlin.io.path.exists

object DiskCacheApiClient : ApiClientWithCache {
    override val playerDataCache = DiskHistoryCache(
        "player-data",
        object : TypeToken<CacheElement<HypixelPlayer>>() {},
        { it.uuid },
        { UUID.fromString(it) }
    )
    override val skyblockProfilesCache = DiskHistoryCache(
        "skyblock-profiles",
        object : TypeToken<CacheElement<SkyblockProfiles>>() {},
        { it.owner },
        { UUID.fromString(it) }
    )
    override val guildCache = DiskHistoryCache(
        "guilds",
        object : TypeToken<CacheElement<Guild>>() {},
        { it.name },
        { it }
    )
    override val bingoDataCache = DiskHistoryCache(
        "bingo-data",
        object : TypeToken<CacheElement<SkyblockBingoData>>() {},
        { it.player },
        { UUID.fromString(it) }
    )

    fun clearCache() {
        val cacheDirectory = Path.of(DiskHistoryCache.cacheDirectory)
        if (cacheDirectory.exists()) {
            DiskHistoryCache.deleteDirectoryContents(cacheDirectory)
        }
    }
}