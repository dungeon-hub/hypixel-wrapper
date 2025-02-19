package net.dungeonhub.hypixel.client

import net.dungeonhub.cache.Cache
import net.dungeonhub.hypixel.entities.guild.Guild
import net.dungeonhub.hypixel.entities.player.HypixelPlayer
import net.dungeonhub.hypixel.entities.skyblock.SkyblockProfiles
import java.time.Instant
import java.util.*

interface ApiClientWithCache : ApiClient {
    val playerDataCache: Cache<HypixelPlayer, UUID>
    val skyblockProfilesCache: Cache<SkyblockProfiles, UUID>
    val guildCache: Cache<Guild, String>

    fun <T : Any> isExpired(cache: Cache<T, UUID>, uuid: UUID, expiresAfterMinutes: Int = 5): Boolean {
        return cache.retrieveElement(uuid)?.timeAdded?.plusSeconds(expiresAfterMinutes * SECONDS_PER_MINUTE)
            ?.isBefore(Instant.now())
            ?: true
    }

    fun <T : Any> isExpired(cache: Cache<T, String>, name: String, expiresAfterMinutes: Int = 5): Boolean {
        return cache.retrieveElement(name)?.timeAdded?.plusSeconds(expiresAfterMinutes * SECONDS_PER_MINUTE)
            ?.isBefore(Instant.now())
            ?: true
    }

    override fun getPlayerData(uuid: UUID): HypixelPlayer? = playerDataCache.retrieve(uuid)

    override fun getSkyblockProfiles(uuid: UUID): SkyblockProfiles? = skyblockProfilesCache.retrieve(uuid)

    override fun getGuild(name: String): Guild? = guildCache.retrieve(name.lowercase())

    companion object {
        const val SECONDS_PER_MINUTE = 60L
    }
}