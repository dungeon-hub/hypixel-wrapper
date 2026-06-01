package net.dungeonhub.hypixel.entities.skyblock.dungeon

import com.google.gson.JsonObject
import net.dungeonhub.provider.getAsJsonPrimitiveOrNull

//TODO complete mapping
class DungeonData(
    val experience: Double?,
    val highestTierCompleted: Int?
)

fun JsonObject.toDungeonData(): DungeonData {
    return DungeonData(
        getAsJsonPrimitiveOrNull("experience")?.asDouble,
        getAsJsonPrimitiveOrNull("highest_tier_completed")?.asInt
    )
}