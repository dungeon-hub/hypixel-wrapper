package net.dungeonhub.hypixel.client

import net.dungeonhub.cache.Cache
import net.dungeonhub.hypixel.entities.bingo.SkyblockBingoData
import net.dungeonhub.hypixel.entities.guild.Guild
import net.dungeonhub.hypixel.entities.player.HypixelPlayer
import net.dungeonhub.hypixel.entities.skyblock.SkyblockProfiles
import net.dungeonhub.hypixel.entities.status.PlayerSession
import java.util.*

class FallbackApiClient(
    val first: ApiClientWithCache,
    val second: ApiClient,
    val expiresAfterMinutes: Int = 5,
    val useStaleCache: Boolean = true
) : ApiClient {
    override fun getPlayerData(uuid: UUID): HypixelPlayer? =
        (if (first.playerDataCache.isExpired(uuid)) null else first.getPlayerData(uuid))
            ?: second.getPlayerData(uuid)
            ?: if (useStaleCache) first.getPlayerData(uuid) else null

    override fun getSession(uuid: UUID): PlayerSession? =
        (if (first.sessionCache.isExpired(uuid)) null else first.getSession(uuid))
            ?: second.getSession(uuid)
            ?: if (useStaleCache) first.getSession(uuid) else null

    override fun getSkyblockProfiles(uuid: UUID): SkyblockProfiles? =
        (if (first.skyblockProfilesCache.isExpired(uuid)) null else first.getSkyblockProfiles(uuid))
            ?: second.getSkyblockProfiles(uuid)
            ?: if (useStaleCache) first.getSkyblockProfiles(uuid) else null

    override fun getGuild(name: String): Guild? =
        (if (first.guildCache.isExpired(name.lowercase())) null else first.getGuild(name))
            ?: second.getGuild(name)
            ?: if (useStaleCache) first.getGuild(name) else null

    override fun getPlayerGuild(uuid: UUID): Guild? =
        (if (first.playerGuildCache.isExpired(uuid)) null else first.getPlayerGuild(uuid))
            ?: second.getPlayerGuild(uuid)
            ?: if (useStaleCache) first.getPlayerGuild(uuid) else null

    override fun getBingoData(uuid: UUID): SkyblockBingoData? =
        (if (first.bingoDataCache.isExpired(uuid)) null else first.getBingoData(uuid))
            ?: second.getBingoData(uuid)
            ?: if (useStaleCache) first.getBingoData(uuid) else null

    fun withCacheExpiration(minutes: Int): FallbackApiClient =
        FallbackApiClient(first, second, minutes, useStaleCache)

    fun withStaleCache(useStaleCache: Boolean = true): FallbackApiClient =
        FallbackApiClient(first, second, expiresAfterMinutes, useStaleCache)

    private fun <T : Any> Cache<T, UUID>.isExpired(uuid: UUID): Boolean {
        return first.isExpired(this, uuid, expiresAfterMinutes)
    }

    private fun <T : Any> Cache<T, String>.isExpired(name: String): Boolean {
        return first.isExpired(this, name, expiresAfterMinutes)
    }
}