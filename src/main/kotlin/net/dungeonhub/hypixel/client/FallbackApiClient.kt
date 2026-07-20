package net.dungeonhub.hypixel.client

import net.dungeonhub.hypixel.client.responses.ApiResponse
import net.dungeonhub.hypixel.client.responses.Unavailable
import net.dungeonhub.hypixel.client.responses.ValueResponse
import net.dungeonhub.hypixel.entities.bingo.SkyblockBingoData
import net.dungeonhub.hypixel.entities.guild.Guild
import net.dungeonhub.hypixel.entities.player.HypixelPlayer
import net.dungeonhub.hypixel.entities.skyblock.SkyblockProfiles
import net.dungeonhub.hypixel.entities.status.PlayerSession
import java.util.*
import kotlin.time.Clock
import kotlin.time.Duration.Companion.minutes

class FallbackApiClient(
    val first: ApiClientWithCache,
    val second: ApiClient,
    val expiresAfterMinutes: Int = 5,
    val useStaleCache: Boolean = true
) : ApiClient {
    override fun getPlayerData(uuid: UUID): ApiResponse<HypixelPlayer> = handleFallback {
        it.getPlayerData(uuid)
    }

    override fun getSession(uuid: UUID): ApiResponse<PlayerSession> = handleFallback {
        it.getSession(uuid)
    }

    override fun getSkyblockProfiles(uuid: UUID): ApiResponse<SkyblockProfiles> = handleFallback {
        it.getSkyblockProfiles(uuid)
    }

    override fun getGuild(name: String): ApiResponse<Guild> = handleFallback {
        it.getGuild(name)
    }

    override fun getPlayerGuild(uuid: UUID): ApiResponse<Guild> = handleFallback {
        it.getPlayerGuild(uuid)
    }

    override fun getBingoData(uuid: UUID): ApiResponse<SkyblockBingoData> = handleFallback {
        it.getBingoData(uuid)
    }

    private fun <T: Any> handleFallback(loadFunction: (ApiClient) -> ApiResponse<T>): ApiResponse<T> {
        val cacheValue = loadFunction(first)
        if(cacheValue is ValueResponse && !cacheValue.isStale()) {
            return cacheValue
        }

        val fallbackData = loadFunction(second)
        if(fallbackData !is Unavailable) {
            return fallbackData
        }

        if(!useStaleCache) return fallbackData

        return cacheValue.asStale()
    }

    fun withCacheExpiration(minutes: Int): FallbackApiClient =
        FallbackApiClient(first, second, minutes, useStaleCache)

    fun withStaleCache(useStaleCache: Boolean = true): FallbackApiClient =
        FallbackApiClient(first, second, expiresAfterMinutes, useStaleCache)

    private fun <T : Any> ValueResponse<T>.isStale(): Boolean {
        return (timestamp + expiresAfterMinutes.minutes) < Clock.System.now()
    }
}