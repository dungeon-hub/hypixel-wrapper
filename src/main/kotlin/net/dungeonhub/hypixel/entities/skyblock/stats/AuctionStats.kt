package net.dungeonhub.hypixel.entities.skyblock.stats

import com.google.gson.JsonObject
import net.dungeonhub.hypixel.entities.inventory.SkyblockRarity
import net.dungeonhub.provider.getAsJsonObjectOrNull
import net.dungeonhub.provider.getAsJsonPrimitiveOrNull

class AuctionStats(
    //Buyer stats
    val bids: Int,
    val won: Int,

    val highestBid: Double,
    val goldSpent: Double,

    val totalBought: Map<SkyblockRarity?, Int>,

    //Seller stats
    val created: Int,
    val completed: Int,
    val noBids: Int,

    val goldEarned: Double,
    val fees: Double,

    val totalSold: Map<SkyblockRarity?, Int>
)

fun JsonObject.toAuctionStats(): AuctionStats {
    return AuctionStats(
        getAsJsonPrimitiveOrNull("bids")?.asInt ?: 0,
        getAsJsonPrimitiveOrNull("won")?.asInt ?: 0,
        getAsJsonPrimitiveOrNull("highest_bid")?.asDouble ?: 0.0,
        getAsJsonPrimitiveOrNull("gold_spent")?.asDouble ?: 0.0,
        getAsJsonObjectOrNull("total_bought")?.entrySet()?.associate {
            (if(it.key != "total") SkyblockRarity.fromApiName(it.key) else null) to it.value.asInt
        } ?: emptyMap(),
        getAsJsonPrimitiveOrNull("created")?.asInt ?: 0,
        getAsJsonPrimitiveOrNull("completed")?.asInt ?: 0,
        getAsJsonPrimitiveOrNull("no_bids")?.asInt ?: 0,
        getAsJsonPrimitiveOrNull("gold_earned")?.asDouble ?: 0.0,
        getAsJsonPrimitiveOrNull("fees")?.asDouble ?: 0.0,
        getAsJsonObjectOrNull("total_sold")?.entrySet()?.associate {
            (if(it.key != "total") SkyblockRarity.fromApiName(it.key) else null) to it.value.asInt
        } ?: emptyMap()
    )
}