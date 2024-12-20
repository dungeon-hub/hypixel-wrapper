package net.dungeonhub.hypixel.entities

import com.google.gson.JsonObject
import net.dungeonhub.provider.getAsJsonPrimitiveOrNull

class MemberLeveling(
    val experience: Int,
    val raw: JsonObject
)

fun JsonObject.toLeveling(): MemberLeveling {
    return MemberLeveling(
        getAsJsonPrimitiveOrNull("experience")?.asInt ?: 0,
        this
    )
}