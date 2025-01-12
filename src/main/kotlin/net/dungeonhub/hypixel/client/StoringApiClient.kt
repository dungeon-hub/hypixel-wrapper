package net.dungeonhub.hypixel.client

import net.dungeonhub.cache.Cache
import net.dungeonhub.hypixel.entities.SkyblockProfiles
import net.dungeonhub.hypixel.entities.player.HypixelPlayer
import java.util.*

class StoringApiClient(val apiClient: ApiClient, val storage: ApiClientWithCache) : ApiClient {
    override fun getPlayerData(uuid: UUID): HypixelPlayer? = storeAndReturn(apiClient.getPlayerData(uuid), storage.playerDataCache)
    override fun getSkyblockProfiles(uuid: UUID): SkyblockProfiles? = storeAndReturn(apiClient.getSkyblockProfiles(uuid), storage.skyblockProfilesCache)

    private fun <T, K> storeAndReturn(value: T?, cache: Cache<T, K>): T? {
        if (value == null) return null
        cache.store(value)
        return value
    }
}