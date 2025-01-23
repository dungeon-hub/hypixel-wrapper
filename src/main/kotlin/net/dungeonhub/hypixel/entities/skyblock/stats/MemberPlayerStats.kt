package net.dungeonhub.hypixel.entities.skyblock.stats

import com.google.gson.JsonObject
import net.dungeonhub.provider.getAsJsonObjectOrNull
import net.dungeonhub.provider.getAsJsonPrimitiveOrNull

//TODO map pet stats
//TODO add dik9 to test data set
class MemberPlayerStats(
    val kills: Map<String, Int>,
    val deaths: Map<String, Int>,
    val auctions: AuctionStats?,
    val petStats: JsonObject?,
    val candyCollected: JsonObject?,
    val spooky: JsonObject?,
    val mythos: JsonObject?,
    val gifts: JsonObject?,
    val races: JsonObject?,
    val endIsland: JsonObject?,
    val rift: JsonObject?,
    val winter: JsonObject?,
    val shredderRodStats: JsonObject?,
    val itemsFished: JsonObject?,
    val seaCreatureKills: Int,
    val highestDamage: Double,
    val highestCritDamage: Double,
    val glowingMushroomsBroken: Int
)

fun JsonObject.toPlayerStats(): MemberPlayerStats {
    return MemberPlayerStats(
        getAsJsonObjectOrNull("kills")?.entrySet()?.associate { it.key to it.value.asInt } ?: emptyMap(),
        getAsJsonObjectOrNull("deaths")?.entrySet()?.associate { it.key to it.value.asInt } ?: emptyMap(),
        getAsJsonObjectOrNull("auctions")?.toAuctionStats(),
        getAsJsonObjectOrNull("pets"),
        getAsJsonObjectOrNull("candy_collected"),
        getAsJsonObjectOrNull("spooky"),
        getAsJsonObjectOrNull("mythos"),
        getAsJsonObjectOrNull("gifts"),
        getAsJsonObjectOrNull("races"),
        getAsJsonObjectOrNull("end_island"),
        getAsJsonObjectOrNull("rift"),
        getAsJsonObjectOrNull("winter"),
        getAsJsonObjectOrNull("shredder_rod"),
        getAsJsonObjectOrNull("items_fished"),
        getAsJsonPrimitiveOrNull("sea_creature_kills")?.asInt ?: 0,
        getAsJsonPrimitiveOrNull("highest_damage")?.asDouble ?: 0.0,
        getAsJsonPrimitiveOrNull("highest_critical_damage")?.asDouble ?: 0.0,
        getAsJsonPrimitiveOrNull("glowing_mushrooms_broken")?.asInt ?: 0
    )
}