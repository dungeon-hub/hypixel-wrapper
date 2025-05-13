package net.dungeonhub.hypixel.client.resources

import net.dungeonhub.cache.memory.CacheElement
import net.dungeonhub.hypixel.client.RestApiClient
import net.dungeonhub.hypixel.entities.bingo.CurrentBingoEvent
import java.time.Instant

object StaticResourceApiClient : ResourceApiClient {
    private const val SECONDS_PER_DAY = 60 * 60 * 24L

    private var currentBingoEvent: CacheElement<CurrentBingoEvent>? = null

    override fun getCurrentBingoEvent(): CurrentBingoEvent? {
        if (currentBingoEvent == null
            || currentBingoEvent?.timeAdded?.plusSeconds(SECONDS_PER_DAY)?.isBefore(Instant.now()) == false
        ) {
            currentBingoEvent = RestApiClient.getCurrentBingoEvent()?.let { CacheElement(Instant.now(), it) }
        }

        return currentBingoEvent?.value
    }
}