package net.dungeonhub.provider

import net.dungeonhub.cache.CacheType
import net.dungeonhub.client.DatabaseCacheApiClient
import net.dungeonhub.client.DiskCacheApiClient
import net.dungeonhub.client.MemoryCacheApiClient
import net.dungeonhub.client.ApiClientWithCache

object CacheApiClientProvider {
    var cacheTypeString: String? = System.getenv("HYPIXEL_API_CACHE_TYPE")

    val cacheType: CacheType
        get() {
            val default = CacheType.Memory

            return try {
                cacheTypeString?.let { CacheType.valueOf(it) } ?: default
            } catch (_: IllegalArgumentException) {
                default
            }
        }

    val client: ApiClientWithCache
        get() {
            return when (cacheType) {
                CacheType.Memory -> MemoryCacheApiClient
                CacheType.Disk -> DiskCacheApiClient
                CacheType.Database -> DatabaseCacheApiClient
            }
        }
}