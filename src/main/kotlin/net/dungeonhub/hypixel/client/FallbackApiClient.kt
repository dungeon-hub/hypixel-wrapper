package net.dungeonhub.hypixel.client

import net.dungeonhub.cache.Cache
import net.dungeonhub.hypixel.entities.SkyblockProfiles
import net.hypixel.api.reply.PlayerReply
import java.util.*

//TODO add tests if values expire correctly
class FallbackApiClient(val first: ApiClientWithCache, val second: ApiClient, var expiresAfterMinutes: Int = 5) :
    ApiClient {
    override fun getPlayerData(uuid: UUID): PlayerReply.Player? =
        (if (first.playerDataCache.isExpired(uuid)) null else first.getPlayerData(uuid)) ?: second.getPlayerData(uuid)

    override fun getSkyblockProfiles(uuid: UUID): SkyblockProfiles? =
        (if (first.skyblockProfilesCache.isExpired(uuid)) null else first.getSkyblockProfiles(uuid))
            ?: second.getSkyblockProfiles(uuid)

    private fun <T : Any> Cache<T, UUID>.isExpired(uuid: UUID): Boolean {
        return first.isExpired(this, uuid, expiresAfterMinutes)
    }
}