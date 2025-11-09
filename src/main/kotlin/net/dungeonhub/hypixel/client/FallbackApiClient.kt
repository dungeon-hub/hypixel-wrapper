package net.dungeonhub.hypixel.client

import net.dungeonhub.cache.Cache
import net.dungeonhub.hypixel.entities.bingo.SkyblockBingoData
import net.dungeonhub.hypixel.entities.guild.Guild
import net.dungeonhub.hypixel.entities.player.HypixelPlayer
import net.dungeonhub.hypixel.entities.skyblock.SkyblockProfiles
import java.util.*

class FallbackApiClient(val first: ApiClientWithCache, val second: ApiClient, val expiresAfterMinutes: Int = 5) :
    ApiClient {
    override fun getPlayerData(uuid: UUID): HypixelPlayer? =
        (if (first.playerDataCache.isExpired(uuid)) null else first.getPlayerData(uuid)) ?: second.getPlayerData(uuid)

    override fun getSkyblockProfiles(uuid: UUID): SkyblockProfiles? =
        (if (first.skyblockProfilesCache.isExpired(uuid)) null else first.getSkyblockProfiles(uuid))
            ?: second.getSkyblockProfiles(uuid)

    override fun getGuild(name: String): Guild? =
        (if (first.guildCache.isExpired(name.lowercase())) null else first.getGuild(name))
            ?: second.getGuild(name)

    override fun getBingoData(uuid: UUID): SkyblockBingoData? =
        (if (first.bingoDataCache.isExpired(uuid)) null else first.getBingoData(uuid))
            ?: second.getBingoData(uuid)

    fun withCacheExpiration(minutes: Int): FallbackApiClient {
        return FallbackApiClient(first, second, minutes)
    }

    private fun <T : Any> Cache<T, UUID>.isExpired(uuid: UUID): Boolean {
        return first.isExpired(this, uuid, expiresAfterMinutes)
    }

    private fun <T : Any> Cache<T, String>.isExpired(name: String): Boolean {
        return first.isExpired(this, name, expiresAfterMinutes)
    }
}