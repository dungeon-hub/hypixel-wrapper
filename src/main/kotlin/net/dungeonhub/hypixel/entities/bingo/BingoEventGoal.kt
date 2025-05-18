package net.dungeonhub.hypixel.entities.bingo

import com.google.gson.JsonObject
import net.dungeonhub.provider.getAsJsonArrayOrNull
import net.dungeonhub.provider.getAsJsonPrimitiveOrNull

class BingoEventGoal(
    val id: String,
    val name: String,
    val tiers: List<Int>?,
    val requiredAmount: Int?,
    val progress: Long?,
    val lore: String,
    val fullLore: List<String>
) {
    val isCommunityGoal: Boolean
        get() = tiers != null
}

fun JsonObject.toBingoEventGoal(): BingoEventGoal {
    return BingoEventGoal(
        getAsJsonPrimitive("id").asString,
        getAsJsonPrimitive("name").asString,
        getAsJsonArrayOrNull("tiers")?.map { it.asInt },
        getAsJsonPrimitiveOrNull("requiredAmount")?.asInt,
        getAsJsonPrimitiveOrNull("progress")?.asLong,
        getAsJsonPrimitive("lore").asString,
        getAsJsonArray("fullLore").map { it.asString }
    )
}