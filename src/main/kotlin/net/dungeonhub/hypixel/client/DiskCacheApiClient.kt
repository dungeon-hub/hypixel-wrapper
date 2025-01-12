package net.dungeonhub.hypixel.client

import com.google.gson.reflect.TypeToken
import net.dungeonhub.cache.disk.DiskHistoryCache
import net.dungeonhub.cache.memory.CacheElement
import net.dungeonhub.hypixel.entities.SkyblockProfiles
import net.dungeonhub.hypixel.entities.player.HypixelPlayer
import java.nio.file.Path
import kotlin.io.path.exists

object DiskCacheApiClient : ApiClientWithCache {
    override val playerDataCache =
        DiskHistoryCache("player-data", object : TypeToken<CacheElement<HypixelPlayer>>() {}) { it.uuid }
    override val skyblockProfilesCache =
        DiskHistoryCache("skyblock-profiles", object : TypeToken<CacheElement<SkyblockProfiles>>() {}) { it.owner }

    fun clearCache() {
        val cacheDirectory = Path.of(DiskHistoryCache.cacheDirectory)
        if(cacheDirectory.exists()) {
            DiskHistoryCache.deleteDirectoryWithContents(cacheDirectory)
        }
    }
}