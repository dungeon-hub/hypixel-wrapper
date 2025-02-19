package net.dungeonhub.hypixel.entities.skyblock

import com.google.gson.JsonObject
import net.dungeonhub.provider.getAsJsonPrimitiveOrNull

data class MemberLeveling(
    val experience: Int,
    val raw: JsonObject
) {
    val level: Int
        get() = experience / EXPERIENCE_PER_LEVEL

    val levelWithProgression: Double
        get() = experience.toDouble() / EXPERIENCE_PER_LEVEL

    companion object {
        const val EXPERIENCE_PER_LEVEL = 100
    }
}

fun JsonObject.toLeveling(): MemberLeveling {
    return MemberLeveling(
        getAsJsonPrimitiveOrNull("experience")?.asInt ?: 0,
        this
    )
}