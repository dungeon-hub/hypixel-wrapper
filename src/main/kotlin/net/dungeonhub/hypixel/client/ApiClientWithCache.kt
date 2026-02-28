package net.dungeonhub.hypixel.client

import net.dungeonhub.cache.Cache
import net.dungeonhub.hypixel.entities.bingo.SkyblockBingoData
import net.dungeonhub.hypixel.entities.guild.Guild
import net.dungeonhub.hypixel.entities.player.HypixelPlayer
import net.dungeonhub.hypixel.entities.skyblock.SkyblockProfiles
import net.dungeonhub.hypixel.entities.status.PlayerSession
import java.time.Instant
import java.util.*

interface ApiClientWithCache : ApiClient {
    val playerDataCache: Cache<HypixelPlayer, UUID>
    val sessionCache: Cache<PlayerSession, UUID>
    val skyblockProfilesCache: Cache<SkyblockProfiles, UUID>
    val guildCache: Cache<Guild, String>
    val playerGuildCache: Cache<Guild, UUID>
    val bingoDataCache: Cache<SkyblockBingoData, UUID>

    fun <T : Any> isExpired(cache: Cache<T, UUID>, uuid: UUID, expiresAfterMinutes: Int = 5): Boolean {
        return cache.retrieveElement(uuid)?.timeAdded?.plusSeconds(expiresAfterMinutes * 60L)?.isBefore(Instant.now())
            ?: true
    }

    fun <T : Any> isExpired(cache: Cache<T, String>, name: String, expiresAfterMinutes: Int = 5): Boolean {
        return cache.retrieveElement(name)?.timeAdded?.plusSeconds(expiresAfterMinutes * 60L)?.isBefore(Instant.now())
            ?: true
    }

    override fun getPlayerData(uuid: UUID): HypixelPlayer? {
        return playerDataCache.retrieve(uuid)
    }

    override fun getSession(uuid: UUID): PlayerSession? {
        return sessionCache.retrieve(uuid)
    }

    override fun getSkyblockProfiles(uuid: UUID): SkyblockProfiles? {
        return skyblockProfilesCache.retrieve(uuid)
    }

    override fun getGuild(name: String): Guild? {
        return guildCache.retrieve(name.lowercase())
    }

    override fun getPlayerGuild(uuid: UUID): Guild? {
        return playerGuildCache.retrieve(uuid)
    }

    override fun getBingoData(uuid: UUID): SkyblockBingoData? {
        return bingoDataCache.retrieve(uuid)
    }
}