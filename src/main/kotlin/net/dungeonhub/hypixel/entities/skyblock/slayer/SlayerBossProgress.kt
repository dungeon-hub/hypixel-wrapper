package net.dungeonhub.hypixel.entities.skyblock.slayer

import com.google.gson.JsonObject
import net.dungeonhub.provider.getAsJsonObjectOrNull
import net.dungeonhub.provider.getAsJsonPrimitiveOrNull

class SlayerBossProgress(
    val claimedLevels: JsonObject,
    /**
     * This maps the tier of the slayer boss (1-5) to the amount the player has killed it.
     */
    val bossKillsPerTier: Map<Int, Int>?,
    /**
     * This maps the tier of the slayer boss (1-5) to the amount the player has attempted to kill it (but failed).
     */
    val bossAttemptsPerTier: Map<Int, Int>?,
    val xp: Int?
)

fun JsonObject.toSlayerProgress(): SlayerBossProgress {
    return SlayerBossProgress(
        getAsJsonObjectOrNull("claimed_levels") ?: JsonObject(),
        entrySet().filter { it.key.startsWith("boss_kills_tier_") }.associate {
            it.key.removePrefix("boss_kills_tier_").toInt() + 1 to it.value.asInt
        },
        entrySet().filter { it.key.startsWith("boss_attempts_tier_") }.associate {
            it.key.removePrefix("boss_attempts_tier_").toInt() + 1 to it.value.asInt
        },
        getAsJsonPrimitiveOrNull("xp")?.asInt
    )
}