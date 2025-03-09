package net.dungeonhub.hypixel.entities.skyblock.rift

import com.google.gson.JsonObject
import net.dungeonhub.hypixel.entities.inventory.MemberInventory
import net.dungeonhub.hypixel.entities.inventory.toMemberInventoryData
import net.dungeonhub.hypixel.entities.skyblock.slayer.SlayerQuest
import net.dungeonhub.hypixel.entities.skyblock.slayer.toSlayerQuest
import net.dungeonhub.provider.getAsJsonArrayOrNull
import net.dungeonhub.provider.getAsJsonObjectOrNull

class RiftData(
    val villagePlaza: JsonObject?,
    val witherCage: JsonObject?,
    val blackLagoon: JsonObject?,
    val deadCats: JsonObject?,
    val wizardTower: JsonObject?,
    val enigma: JsonObject?,
    val gallery: JsonObject?,
    val slayerQuest: SlayerQuest?,
    val lifetimePurchasedBoundaries: List<String>,
    val westVillage: JsonObject?,
    val wyldWoods: JsonObject?,
    val castle: JsonObject?,
    val access: JsonObject?,
    val dreadfarm: JsonObject?,
    val inventory: MemberInventory?
)

fun JsonObject.toRiftData(): RiftData {
    return RiftData(
        getAsJsonObjectOrNull("village_plaza"),
        getAsJsonObjectOrNull("wither_cage"),
        getAsJsonObjectOrNull("black_lagoon"),
        getAsJsonObjectOrNull("dead_cats"),
        getAsJsonObjectOrNull("wizard_tower"),
        getAsJsonObjectOrNull("enigma"),
        getAsJsonObjectOrNull("gallery"),
        getAsJsonObjectOrNull("slayer_quest")?.toSlayerQuest(),
        getAsJsonArrayOrNull("lifetime_purchased_boundaries")?.asList()?.map { it.asString } ?: emptyList(),
        getAsJsonObjectOrNull("west_village"),
        getAsJsonObjectOrNull("wyld_woods"),
        getAsJsonObjectOrNull("castle"),
        getAsJsonObjectOrNull("access"),
        getAsJsonObjectOrNull("dreadfarm"),
        getAsJsonObjectOrNull("inventory")?.toMemberInventoryData()
    )
}