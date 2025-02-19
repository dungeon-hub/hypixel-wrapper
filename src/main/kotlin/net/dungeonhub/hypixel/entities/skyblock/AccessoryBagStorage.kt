package net.dungeonhub.hypixel.entities.skyblock

import com.google.gson.JsonObject
import net.dungeonhub.provider.getAsJsonArrayOrNull
import net.dungeonhub.provider.getAsJsonObjectOrNull
import net.dungeonhub.provider.getAsJsonPrimitiveOrNull

data class AccessoryBagStorage(
    val selectedPower: String?,
    val upgradesPurchased: Int,
    val highestMagicalPower: Int,
    val unlockedPowers: List<String>,
    val tuning: JsonObject?,
    val raw: JsonObject
)

fun JsonObject.toAccessoryBagStorage(): AccessoryBagStorage {
    return AccessoryBagStorage(
        getAsJsonPrimitiveOrNull("selected_power")?.asString,
        getAsJsonPrimitiveOrNull("upgrades_purchased")?.asInt ?: 0,
        getAsJsonPrimitiveOrNull("highest_magical_power")?.asInt ?: 0,
        getAsJsonArrayOrNull("unlocked_powers")?.map { it.asString } ?: emptyList(),
        getAsJsonObjectOrNull("tuning"),
        this
    )
}