package net.dungeonhub.hypixel.entities.inventory

import com.google.gson.JsonObject
import net.dungeonhub.provider.getAsJsonPrimitiveOrNull

class MemberInventory(
    val inventoryContents: InventoryContent,
    val enderChestContent: InventoryContent?,
    val backpackIcons: Map<Int, InventoryContent>,
    val bagContents: Map<String, InventoryContent>,
    val armor: InventoryContent?,
    val equipment: InventoryContent?,
    val personalVault: InventoryContent?,
    val equippedWardrobeSlot: Int?,
    val backpackContents: Map<Int, InventoryContent>,
    val sackCounts: Map<String, Int>,
    val wardrobeContents: InventoryContent?
)

fun JsonObject.toMemberInventoryData(): MemberInventory {
    return MemberInventory(
        getAsJsonObject("inv_contents").toInventoryContentData(),
        getAsJsonObject("ender_chest_contents")?.toInventoryContentData(),
        getAsJsonObject("backpack_icons")?.entrySet()
            ?.associate { it.key.toInt() to it.value.asJsonObject.toInventoryContentData() }
            ?: emptyMap(),
        getAsJsonObject("bag_contents")?.entrySet()
            ?.associate { it.key to it.value.asJsonObject.toInventoryContentData() } ?: emptyMap(),
        getAsJsonObject("inv_armor").toInventoryContentData(),
        getAsJsonObject("equipment_contents").toInventoryContentData(),
        getAsJsonObject("personal_vault_contents").toInventoryContentData(),
        getAsJsonPrimitiveOrNull("wardrobe_equipped_slot")?.asInt,
        getAsJsonObject("backpack_contents")?.entrySet()
            ?.associate { it.key.toInt() to it.value.asJsonObject.toInventoryContentData() } ?: emptyMap(),
        getAsJsonObject("sacks_counts")?.entrySet()?.associate { it.key to it.value.asInt } ?: emptyMap(),
        getAsJsonObject("wardrobe_contents").toInventoryContentData()
    )
}