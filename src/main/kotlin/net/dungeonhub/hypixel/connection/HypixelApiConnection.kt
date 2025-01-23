package net.dungeonhub.hypixel.connection

import net.dungeonhub.hypixel.client.ApiClient
import net.dungeonhub.hypixel.client.FallbackApiClient
import net.dungeonhub.hypixel.entities.skyblock.SkyblockProfiles
import net.dungeonhub.hypixel.entities.player.HypixelPlayer
import net.dungeonhub.strategy.ApiClientStrategy
import java.time.Duration
import java.util.*

class HypixelApiConnection(val strategy: ApiClientStrategy = ApiClientStrategy.CacheWithRestFallback) : ApiClient {
    var client = strategy.client
        private set

    override fun getPlayerData(uuid: UUID): HypixelPlayer? = strategy.client.getPlayerData(uuid)
    override fun getSkyblockProfiles(uuid: UUID): SkyblockProfiles? = strategy.client.getSkyblockProfiles(uuid)

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
}