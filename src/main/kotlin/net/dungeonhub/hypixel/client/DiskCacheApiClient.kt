package net.dungeonhub.hypixel.client

import com.google.gson.reflect.TypeToken
import net.dungeonhub.cache.disk.DiskHistoryStringCache
import net.dungeonhub.cache.disk.DiskHistoryUUIDCache
import net.dungeonhub.cache.memory.CacheElement
import net.dungeonhub.hypixel.entities.bingo.SkyblockBingoData
import net.dungeonhub.hypixel.entities.guild.Guild
import net.dungeonhub.hypixel.entities.player.HypixelPlayer
import net.dungeonhub.hypixel.entities.skyblock.SkyblockProfiles
import java.nio.file.Path
import kotlin.io.path.exists

object DiskCacheApiClient : ApiClientWithCache {
    override val playerDataCache =
        DiskHistoryUUIDCache("player-data", object : TypeToken<CacheElement<HypixelPlayer>>() {}) { it.uuid }
    override val skyblockProfilesCache =
        DiskHistoryUUIDCache("skyblock-profiles", object : TypeToken<CacheElement<SkyblockProfiles>>() {}) { it.owner }
    override val guildCache =
        DiskHistoryStringCache("guilds", object : TypeToken<CacheElement<Guild>>() {}) { it.name }
    override val bingoDataCache =
        DiskHistoryUUIDCache("bingo-data", object : TypeToken<CacheElement<SkyblockBingoData>>() {}) { it.player }

    fun clearCache() {
        val cacheDirectory = Path.of(DiskHistoryUUIDCache.cacheDirectory)
        if(cacheDirectory.exists()) {
            DiskHistoryUUIDCache.deleteDirectoryWithContents(cacheDirectory)
        }
    }
}