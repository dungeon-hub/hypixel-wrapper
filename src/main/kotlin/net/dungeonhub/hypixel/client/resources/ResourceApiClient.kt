package net.dungeonhub.hypixel.client.resources

import net.dungeonhub.hypixel.entities.bingo.CurrentBingoEvent

interface ResourceApiClient {
    fun getCurrentBingoEvent(): CurrentBingoEvent?
}