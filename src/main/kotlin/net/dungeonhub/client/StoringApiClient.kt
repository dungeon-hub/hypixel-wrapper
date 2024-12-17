package net.dungeonhub.client

import net.dungeonhub.cache.Cache
import net.hypixel.api.reply.PlayerReply
import java.util.*

class StoringApiClient(val apiClient: ApiClient, val storage: ApiClientWithCache) : ApiClient {
    override fun getPlayerData(uuid: UUID): PlayerReply.Player? = storeAndReturn(apiClient.getPlayerData(uuid), storage.playerDataCache)

    private fun <T, K> storeAndReturn(value: T?, cache: Cache<T, K>): T? {
        if (value == null) return null
        cache.store(value)
        return value
    }
}