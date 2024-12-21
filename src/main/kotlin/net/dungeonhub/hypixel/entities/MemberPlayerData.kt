package net.dungeonhub.hypixel.entities

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import net.dungeonhub.provider.getAsJsonArrayOrNull
import net.dungeonhub.provider.getAsJsonObjectOrNull
import net.dungeonhub.provider.getAsJsonPrimitiveOrNull
import java.math.BigDecimal
import java.time.Instant

class MemberPlayerData(
    val experience: List<Pair<String, BigDecimal>>?,
    val visitedZones: JsonArray?,
    val lastDeath: Instant?,
    val raw: JsonObject
)

fun JsonObject.toPlayerData(): MemberPlayerData {
    return MemberPlayerData(
        getAsJsonObjectOrNull("experience")?.entrySet()?.map { it.key to it.value.asBigDecimal },
        getAsJsonArrayOrNull("visited_zones"),
        getAsJsonPrimitiveOrNull("last_death")?.asLong?.fromSkyblockTime(),
        this
    )
}

/**
 * Funny story: Skyblock measures some timestamps (e.g. last death) in Skyblock Time, which is the time since time was measured in Skyblock (at 11th June 2019, 17:55:00).
 * This function converts a Skyblock Time timestamp to a Java Instant.
 */
fun Long.fromSkyblockTime(): Instant {
    return Instant.ofEpochSecond(1_560_275_700L + this)
}