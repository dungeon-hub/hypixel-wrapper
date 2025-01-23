package net.dungeonhub.hypixel.entities.skyblock.misc

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import net.dungeonhub.hypixel.entities.skyblock.KnownSkill
import net.dungeonhub.hypixel.entities.skyblock.Skill
import net.dungeonhub.provider.getAsJsonArrayOrNull
import net.dungeonhub.provider.getAsJsonObjectOrNull
import net.dungeonhub.provider.getAsJsonPrimitiveOrNull
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.Instant

class MemberPlayerData(
    val experience: MutableMap<Skill, Double>?,
    val visitedZones: JsonArray?,
    val lastDeath: Instant?,
    val raw: JsonObject
) {
    val nonCosmeticExperience: Map<KnownSkill, Double>?
        get() {
            return experience?.filter { it.key is KnownSkill }?.mapKeys { it.key as KnownSkill }
                ?.filter { !it.key.cosmetic }
        }

    val skillAverage: Double
        get() {
            return BigDecimal(
                nonCosmeticExperience?.mapValues { it.key.calculateLevel(it.value) }?.values?.average() ?: 0.0
            ).setScale(2, RoundingMode.HALF_UP).toDouble()
        }

    fun adjustSocialSkill(actual: Double) {
        if (experience?.containsKey(KnownSkill.Social) == true) {
            experience[KnownSkill.Social] = actual
        }
    }
}

fun JsonObject.toPlayerData(): MemberPlayerData {
    val memberData = MemberPlayerData(
        getAsJsonObjectOrNull("experience")?.entrySet()
            ?.associate { KnownSkill.fromApiName(it.key) to it.value.asDouble }?.toMutableMap(),
        getAsJsonArrayOrNull("visited_zones"),
        getAsJsonPrimitiveOrNull("last_death")?.asLong?.fromSkyblockTime(),
        this
    )

    return memberData
}

/**
 * Funny story: Skyblock measures some timestamps (e.g. last death) in Skyblock Time, which is the time since time was measured in Skyblock (at 11th June 2019, 17:55:00).
 * This function converts a Skyblock Time timestamp to a Java Instant.
 */
fun Long.fromSkyblockTime(): Instant {
    return Instant.ofEpochSecond(1_560_275_700L + this)
}