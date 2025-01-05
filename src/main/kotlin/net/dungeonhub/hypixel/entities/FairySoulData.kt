package net.dungeonhub.hypixel.entities

import com.google.gson.JsonObject
import net.dungeonhub.provider.getAsJsonPrimitiveOrNull

class FairySoulData(
    val numberOfExchanges: Int,
    val totalCollected: Int,
    val unspentSouls: Int,
    val raw: JsonObject
) {
    val totalExchanged: Int
        get() = numberOfExchanges * 5
}

fun JsonObject.toFairySoulData(): FairySoulData {
    return FairySoulData(
        getAsJsonPrimitiveOrNull("fairy_exchanges")?.asInt ?: 0,
        getAsJsonPrimitiveOrNull("total_collected")?.asInt ?: 0,
        getAsJsonPrimitiveOrNull("unspent_souls")?.asInt ?: 0,
        this
    )
}