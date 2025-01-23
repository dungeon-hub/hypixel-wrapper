package net.dungeonhub.hypixel.client

import net.dungeonhub.cache.Cache
import net.dungeonhub.hypixel.entities.skyblock.SkyblockProfiles
import net.dungeonhub.hypixel.entities.player.HypixelPlayer
import java.time.Instant
import java.util.*

interface ApiClientWithCache : ApiClient {
    val playerDataCache: Cache<HypixelPlayer, UUID>
    val skyblockProfilesCache: Cache<SkyblockProfiles, UUID>

    fun <T : Any> isExpired(cache: Cache<T, UUID>, uuid: UUID, expiresAfterMinutes: Int = 5): Boolean {
        return cache.retrieveElement(uuid)?.timeAdded?.plusSeconds(expiresAfterMinutes * 60L)?.isBefore(Instant.now())
            ?: true
    }

    override fun getPlayerData(uuid: UUID): HypixelPlayer? {
        return playerDataCache.retrieve(uuid)
    }

    override fun getSkyblockProfiles(uuid: UUID): SkyblockProfiles? {
        return skyblockProfilesCache.retrieve(uuid)
    }
}