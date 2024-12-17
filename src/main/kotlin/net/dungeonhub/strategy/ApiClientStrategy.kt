package net.dungeonhub.strategy

import net.dungeonhub.provider.CacheApiClientProvider
import net.dungeonhub.client.FallbackApiClient
import net.dungeonhub.client.RestApiClient
import net.dungeonhub.client.StoringApiClient
import net.dungeonhub.client.ApiClient

enum class ApiClientStrategy(val client: ApiClient) {
    Rest(RestApiClient),
    Cache(CacheApiClientProvider.client),
    CachingRest(StoringApiClient(Rest.client, CacheApiClientProvider.client)),
    CacheWithRestFallback(FallbackApiClient(CacheApiClientProvider.client, CachingRest.client));

    companion object {
        val default = CacheWithRestFallback
    }
}