package net.dungeonhub.hypixel.entities.skyblock.slayer

import com.google.gson.JsonObject
import net.dungeonhub.provider.getAsJsonObjectOrNull
import net.dungeonhub.provider.getAsJsonPrimitiveOrNull

class SlayerBossProgress(
    val claimedLevels: JsonObject,
    val xp: Int?
)

fun JsonObject.toSlayerProgress(): SlayerBossProgress {
    return SlayerBossProgress(
        getAsJsonObjectOrNull("claimed_levels") ?: JsonObject(),
        getAsJsonPrimitiveOrNull("xp")?.asInt
    )
}