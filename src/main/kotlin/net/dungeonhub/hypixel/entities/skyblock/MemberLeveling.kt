package net.dungeonhub.hypixel.entities.skyblock

import com.google.gson.JsonObject
import net.dungeonhub.provider.getAsJsonPrimitiveOrNull

class MemberLeveling(
    val experience: Int
) {
    val level: Int
        get() = experience / 100
}

fun JsonObject.toLeveling(): MemberLeveling {
    return MemberLeveling(
        getAsJsonPrimitiveOrNull("experience")?.asInt ?: 0
    )
}