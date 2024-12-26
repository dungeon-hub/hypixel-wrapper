package net.dungeonhub.hypixel.connection

import net.dungeonhub.hypixel.client.ApiClient
import net.dungeonhub.hypixel.client.FallbackApiClient
import net.dungeonhub.hypixel.entities.SkyblockProfiles
import net.dungeonhub.strategy.ApiClientStrategy
import net.hypixel.api.reply.PlayerReply
import java.time.Duration
import java.util.*

class HypixelApiConnection(val strategy: ApiClientStrategy = ApiClientStrategy.CacheWithRestFallback): ApiClient {
    override fun getPlayerData(uuid: UUID): PlayerReply.Player? = strategy.client.getPlayerData(uuid)
    override fun getSkyblockProfiles(uuid: UUID): SkyblockProfiles? = strategy.client.getSkyblockProfiles(uuid)

    var cacheExpiration: Int?
        get() {
            return if(strategy.client is FallbackApiClient) {
                strategy.client.expiresAfterMinutes
            } else {
                null
            }
        }
        set(value) {
            if(strategy.client is FallbackApiClient && value != null) {
                strategy.client.withCacheExpiration(value)
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