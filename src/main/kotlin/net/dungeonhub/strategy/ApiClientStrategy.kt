package net.dungeonhub.strategy

import net.dungeonhub.hypixel.client.ApiClient
import net.dungeonhub.hypixel.client.FallbackApiClient
import net.dungeonhub.hypixel.client.RestApiClient
import net.dungeonhub.hypixel.client.StoringApiClient
import net.dungeonhub.hypixel.provider.CacheApiClientProvider

enum class ApiClientStrategy(val client: ApiClient) {
    Rest(RestApiClient),
    Cache(CacheApiClientProvider.client),
    CachingRest(StoringApiClient(Rest.client, CacheApiClientProvider.client)),
    CacheWithRestFallback(FallbackApiClient(CacheApiClientProvider.client, CachingRest.client));

    companion object {
        val default = CacheWithRestFallback
    }
}