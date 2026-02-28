package net.dungeonhub.hypixel.entities.skyblock.currencies

import com.google.gson.JsonObject
import net.dungeonhub.provider.getAsJsonArrayOrNull

class ProfileBankingData(
    val balance: Double,
    val transactions: List<JsonObject>
)

fun JsonObject.toProfileBankingData(): ProfileBankingData {
    return ProfileBankingData(
        getAsJsonPrimitive("balance")?.asDouble ?: 0.0,
        getAsJsonArrayOrNull("transactions")?.map { it.asJsonObject } ?: emptyList()
    )
}