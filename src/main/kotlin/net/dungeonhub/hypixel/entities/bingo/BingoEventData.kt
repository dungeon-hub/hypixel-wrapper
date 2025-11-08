package net.dungeonhub.hypixel.entities.bingo

import com.google.gson.JsonObject
import net.dungeonhub.provider.getAsJsonArrayOrNull

class BingoEventData(
    val event: Int,
    val points: Int,
    val completedGoals: List<String>
)

fun JsonObject.toBingoEventData(): BingoEventData {
    return BingoEventData(
        getAsJsonPrimitive("key").asInt,
        getAsJsonPrimitive("points").asInt,
        getAsJsonArrayOrNull("completed_goals")?.map { it.asString } ?: emptyList()
    )
}