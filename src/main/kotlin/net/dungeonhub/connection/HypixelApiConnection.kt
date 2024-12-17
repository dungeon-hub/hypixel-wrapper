package net.dungeonhub.connection

import net.dungeonhub.client.ApiClient
import net.dungeonhub.strategy.ApiClientStrategy
import net.hypixel.api.reply.PlayerReply
import java.util.*

class HypixelApiConnection(val strategy: ApiClientStrategy = ApiClientStrategy.CacheWithRestFallback): ApiClient {
    override fun getPlayerData(uuid: UUID): PlayerReply.Player? = strategy.client.getPlayerData(uuid)

    fun withStrategy(strategy: ApiClientStrategy): HypixelApiConnection {
        return HypixelApiConnection(strategy)
    }
}