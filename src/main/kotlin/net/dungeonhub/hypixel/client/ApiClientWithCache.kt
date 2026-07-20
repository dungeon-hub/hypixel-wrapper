package net.dungeonhub.hypixel.client

import net.dungeonhub.cache.Cache
import net.dungeonhub.hypixel.client.responses.ApiResponse
import net.dungeonhub.hypixel.client.responses.DataOrigin
import net.dungeonhub.hypixel.client.responses.Successful
import net.dungeonhub.hypixel.client.responses.Unavailable
import net.dungeonhub.hypixel.entities.bingo.SkyblockBingoData
import net.dungeonhub.hypixel.entities.guild.Guild
import net.dungeonhub.hypixel.entities.player.HypixelPlayer
import net.dungeonhub.hypixel.entities.skyblock.SkyblockProfiles
import net.dungeonhub.hypixel.entities.status.PlayerSession
import java.util.*
import kotlin.time.toKotlinInstant

interface ApiClientWithCache : ApiClient {
    val playerDataCache: Cache<HypixelPlayer, UUID>
    val sessionCache: Cache<PlayerSession, UUID>
    val skyblockProfilesCache: Cache<SkyblockProfiles, UUID>
    val guildCache: Cache<Guild, String>
    val playerGuildCache: Cache<Guild, UUID>
    val bingoDataCache: Cache<SkyblockBingoData, UUID>

    override fun getPlayerData(uuid: UUID): ApiResponse<HypixelPlayer> {
        return playerDataCache.retrieveElement(uuid)?.let { Successful(it.value, DataOrigin.Cache, it.timeAdded.toKotlinInstant()) } ?: Unavailable()
    }

    override fun getSession(uuid: UUID): ApiResponse<PlayerSession> {
        return sessionCache.retrieveElement(uuid)?.let { Successful(it.value, DataOrigin.Cache, it.timeAdded.toKotlinInstant()) } ?: Unavailable()
    }

    override fun getSkyblockProfiles(uuid: UUID): ApiResponse<SkyblockProfiles> {
        return skyblockProfilesCache.retrieveElement(uuid)?.let { Successful(it.value, DataOrigin.Cache, it.timeAdded.toKotlinInstant()) } ?: Unavailable()
    }

    override fun getGuild(name: String): ApiResponse<Guild> {
        return guildCache.retrieveElement(name.lowercase())?.let { Successful(it.value, DataOrigin.Cache, it.timeAdded.toKotlinInstant()) } ?: Unavailable()
    }

    override fun getPlayerGuild(uuid: UUID): ApiResponse<Guild> {
        return playerGuildCache.retrieveElement(uuid)?.let { Successful(it.value, DataOrigin.Cache, it.timeAdded.toKotlinInstant()) } ?: Unavailable()
    }

    override fun getBingoData(uuid: UUID): ApiResponse<SkyblockBingoData> {
        return bingoDataCache.retrieveElement(uuid)?.let { Successful(it.value, DataOrigin.Cache, it.timeAdded.toKotlinInstant()) } ?: Unavailable()
    }
}