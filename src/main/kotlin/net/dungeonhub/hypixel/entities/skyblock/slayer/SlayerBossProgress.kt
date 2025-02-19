package net.dungeonhub.hypixel.entities.skyblock.slayer

import com.google.gson.JsonObject
import net.dungeonhub.provider.getAsJsonObjectOrNull
import net.dungeonhub.provider.getAsJsonPrimitiveOrNull

data class SlayerBossProgress(
    val claimedLevels: JsonObject,
    val xp: Int?,
    val raw: JsonObject
)

fun JsonObject.toSlayerProgress(): SlayerBossProgress {
    return SlayerBossProgress(
        getAsJsonObjectOrNull("claimed_levels") ?: JsonObject(),
        getAsJsonPrimitiveOrNull("xp")?.asInt,
        this
    )
}