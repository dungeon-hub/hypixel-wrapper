package net.dungeonhub.hypixel.connection

import net.dungeonhub.hypixel.client.ApiClient
import net.dungeonhub.hypixel.client.FallbackApiClient
import net.dungeonhub.hypixel.client.resources.ResourceApiClient
import net.dungeonhub.hypixel.client.resources.StaticResourceApiClient
import net.dungeonhub.hypixel.entities.bingo.CurrentBingoEvent
import net.dungeonhub.hypixel.entities.bingo.SkyblockBingoData
import net.dungeonhub.hypixel.entities.guild.Guild
import net.dungeonhub.hypixel.entities.player.HypixelPlayer
import net.dungeonhub.hypixel.entities.skyblock.SkyblockProfiles
import net.dungeonhub.hypixel.entities.status.PlayerSession
import net.dungeonhub.strategy.ApiClientStrategy
import java.time.Duration
import java.util.*

class HypixelApiConnection(val strategy: ApiClientStrategy = ApiClientStrategy.CacheWithRestFallback) : ApiClient,
    ResourceApiClient {
    var client = strategy.client
        private set

    override fun getPlayerData(uuid: UUID): HypixelPlayer? = client.getPlayerData(uuid)
    override fun getSession(uuid: UUID): PlayerSession? = client.getSession(uuid)
    override fun getSkyblockProfiles(uuid: UUID): SkyblockProfiles? = client.getSkyblockProfiles(uuid)
    override fun getGuild(name: String): Guild? = client.getGuild(name)
    override fun getBingoData(uuid: UUID): SkyblockBingoData? = client.getBingoData(uuid)

    var cacheExpiration: Int?
        get() {
            return if (client is FallbackApiClient) {
                (client as FallbackApiClient).expiresAfterMinutes
            } else {
                null
            }
        }
        set(value) {
            if (client is FallbackApiClient && value != null) {
                client = (client as FallbackApiClient).withCacheExpiration(value)
            }
        }

    fun withStrategy(strategy: ApiClientStrategy): HypixelApiConnection {
        return HypixelApiConnection(strategy)
    }

    fun withCacheExpiration(cacheExpiration: Int): HypixelApiConnection {
        val connection = HypixelApiConnection(strategy)
        connection.cacheExpiration = cacheExpiration
        return connection
    }

    fun withCacheExpiration(cacheExpiration: Duration): HypixelApiConnection {
        return withCacheExpiration(cacheExpiration.toMinutes().toInt())
    }

    override fun getCurrentBingoEvent(): CurrentBingoEvent? {
        return StaticResourceApiClient.getCurrentBingoEvent()
    }
}