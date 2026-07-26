package net.dungeonhub.hypixel.client

import net.dungeonhub.cache.Cache
import net.dungeonhub.hypixel.client.responses.ApiResponse
import net.dungeonhub.hypixel.client.responses.ValueResponse
import java.util.*

class StoringApiClient(val apiClient: ApiClient, val storage: ApiClientWithCache) : ApiClient {
    override fun getPlayerData(uuid: UUID) = storeAndReturn(apiClient.getPlayerData(uuid), storage.playerDataCache)
    override fun getSession(uuid: UUID) = storeAndReturn(apiClient.getSession(uuid), storage.sessionCache)
    override fun getSkyblockProfiles(uuid: UUID) = storeAndReturn(apiClient.getSkyblockProfiles(uuid), storage.skyblockProfilesCache)
    override fun getGuild(name: String) = storeAndReturn(apiClient.getGuild(name.lowercase()), storage.guildCache)
    override fun getPlayerGuild(uuid: UUID) = storeAndReturn(apiClient.getPlayerGuild(uuid), storage.playerGuildCache)
    override fun getBingoData(uuid: UUID) = storeAndReturn(apiClient.getBingoData(uuid), storage.bingoDataCache)

    private fun <T, K> storeAndReturn(value: ApiResponse<T>, cache: Cache<T, K>): ApiResponse<T> {
        if (value !is ValueResponse<T>) return value
        cache.store(value.value)
        return value
    }
}