package net.dungeonhub.hypixel.entities.inventory

import com.google.gson.JsonObject
import net.dungeonhub.provider.getAsJsonObjectOrNull
import net.dungeonhub.provider.getAsJsonPrimitiveOrNull

// TODO field ender_chest_page_icons
class MemberInventory(
    val inventoryContents: InventoryContent?,
    val enderChestContent: InventoryContent?,
    val backpackContents: Map<Int, InventoryContent>,
    val backpackIcons: Map<Int, InventoryContent>,
    val bagContents: Map<String, InventoryContent>,
    val armor: InventoryContent?,
    val equipment: InventoryContent?,
    val personalVault: InventoryContent?,
    val equippedWardrobeSlot: Int?,
    val sackCounts: Map<String, Int>,
    val wardrobeContents: InventoryContent?
) {
    val allItems: List<InventoryContent>
        get() {
            val result: MutableList<InventoryContent> = listOfNotNull(inventoryContents, enderChestContent, armor, equipment, personalVault, wardrobeContents).toMutableList()

            result.addAll(backpackContents.values)
            result.addAll(bagContents.values)

            return result
        }
}

fun JsonObject.toMemberInventoryData(): MemberInventory {
    return MemberInventory(
        getAsJsonObjectOrNull("inv_contents")?.toInventoryContentData(),
        getAsJsonObject("ender_chest_contents")?.toInventoryContentData(),
        getAsJsonObject("backpack_contents")?.entrySet()
            ?.associate { it.key.toInt() to it.value.asJsonObject.toInventoryContentData() } ?: emptyMap(),
        getAsJsonObject("backpack_icons")?.entrySet()
            ?.associate { it.key.toInt() to it.value.asJsonObject.toInventoryContentData() }
            ?: emptyMap(),
        getAsJsonObject("bag_contents")?.entrySet()
            ?.associate { it.key to it.value.asJsonObject.toInventoryContentData() } ?: emptyMap(),
        getAsJsonObjectOrNull("inv_armor")?.toInventoryContentData(),
        getAsJsonObjectOrNull("equipment_contents")?.toInventoryContentData(),
        getAsJsonObjectOrNull("personal_vault_contents")?.toInventoryContentData(),
        getAsJsonPrimitiveOrNull("wardrobe_equipped_slot")?.asInt,
        getAsJsonObject("sacks_counts")?.entrySet()?.associate { it.key to it.value.asInt } ?: emptyMap(),
        getAsJsonObjectOrNull("wardrobe_contents")?.toInventoryContentData()
    )
}