package net.dungeonhub.hypixel.entities

import com.google.gson.JsonObject
import net.dungeonhub.provider.getAsJsonObjectOrNull
import net.dungeonhub.provider.getAsJsonPrimitiveOrNull

class SlayerBossProgress(
    val claimedLevels: JsonObject?,
    val xp: Int?,
    val raw: JsonObject
)

fun JsonObject.toSlayerProgress(): SlayerBossProgress {
    return SlayerBossProgress(
        getAsJsonObjectOrNull("claimed_levels"),
        getAsJsonPrimitiveOrNull("xp")?.asInt,
        this
    )
}