package net.dungeonhub.hypixel.client

import com.google.gson.reflect.TypeToken
import net.dungeonhub.cache.disk.DiskHistoryCache
import net.dungeonhub.cache.memory.CacheElement
import net.dungeonhub.hypixel.entities.SkyblockProfiles
import net.hypixel.api.reply.PlayerReply
import java.nio.file.Path
import kotlin.io.path.exists

object DiskCacheApiClient : ApiClientWithCache {
    override val playerDataCache =
        DiskHistoryCache(object : TypeToken<CacheElement<PlayerReply.Player>>() {}) { it.uuid }
    override val skyblockProfilesCache =
        DiskHistoryCache(object : TypeToken<CacheElement<SkyblockProfiles>>() {}) { it.owner }

    fun clearCache() {
        val cacheDirectory = Path.of(DiskHistoryCache.cacheDirectory)
        if(cacheDirectory.exists()) {
            DiskHistoryCache.deleteDirectoryWithContents(cacheDirectory)
        }
    }
}