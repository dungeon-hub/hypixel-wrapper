package net.dungeonhub.hypixel.entities.bingo

import kotlin.time.Duration

enum class KnownBingoEventType(override val apiName: String, val duration: Duration = Duration.parse("7d")) :
    BingoEventType {
    Normal("NORMAL"),
    Secret("SECRET", duration = Duration.parse("14d")),
    Extreme("EXTREME", duration = Duration.parse("14d"));

    class UnknownBingoEventType(override val apiName: String) : BingoEventType

    companion object {
        fun fromApiName(apiName: String): BingoEventType {
            return entries.firstOrNull { it.apiName == apiName } ?: UnknownBingoEventType(apiName)
        }
    }
}