package net.dungeonhub.hypixel.entities

import com.google.gson.JsonObject
import java.time.Instant

class SlayerQuest(
    val type: SlayerType,
    val tier: Int,
    val startTime: Instant,
    val completionState: Int,
    val raw: JsonObject
)

fun JsonObject.toSlayerQuest(): SlayerQuest {
    return SlayerQuest(
        SlayerType.fromApiName(getAsJsonPrimitive("type").asString),
        getAsJsonPrimitive("tier").asInt,
        Instant.ofEpochMilli(getAsJsonPrimitive("start_timestamp").asLong),
        getAsJsonPrimitive("completion_state").asInt,
        this
    )
}