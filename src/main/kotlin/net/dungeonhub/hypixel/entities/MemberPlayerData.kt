package net.dungeonhub.hypixel.entities

import com.google.gson.JsonObject
import net.dungeonhub.provider.getAsJsonObjectOrNull
import java.math.BigDecimal

class MemberPlayerData(
    val experience: List<Pair<String, BigDecimal>>?,
    val raw: JsonObject
)

fun JsonObject.toPlayerData(): MemberPlayerData {
    return MemberPlayerData(
        getAsJsonObjectOrNull("experience")?.entrySet()?.map { it.key to it.value.asBigDecimal },
        this
    )
}