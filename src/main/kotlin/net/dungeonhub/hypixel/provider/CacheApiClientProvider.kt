package net.dungeonhub.hypixel.provider

import net.dungeonhub.cache.CacheType
import net.dungeonhub.hypixel.client.ApiClientWithCache
import net.dungeonhub.hypixel.client.CacheApiClient

object CacheApiClientProvider {
    var cacheTypeString: String? = System.getenv("HYPIXEL_API_CACHE_TYPE")

    var cacheType: CacheType? = null
        get() {
            val default = field

            return try {
                cacheTypeString?.let { CacheType.valueOf(it) } ?: default
            } catch (_: IllegalArgumentException) {
                default
            }
        }

    val client: ApiClientWithCache by lazy { CacheApiClient() }
}