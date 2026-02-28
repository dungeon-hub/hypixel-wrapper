package net.dungeonhub.hypixel.entities.skyblock.slayer

import com.google.gson.JsonObject
import net.dungeonhub.provider.getAsJsonObjectOrNull

class MemberSlayerData(
    val activeSlayerQuest: SlayerQuest?,
    val slayerProgress: Map<SlayerType, SlayerBossProgress>
)

fun JsonObject.toSlayerData(): MemberSlayerData {
    return MemberSlayerData(
        getAsJsonObjectOrNull("slayer_quest")?.toSlayerQuest(),
        getAsJsonObjectOrNull("slayer_bosses")?.entrySet()?.associate {
            KnownSlayerType.fromApiName(it.key) to it.value.asJsonObject.toSlayerProgress()
        } ?: emptyMap()
    )
}