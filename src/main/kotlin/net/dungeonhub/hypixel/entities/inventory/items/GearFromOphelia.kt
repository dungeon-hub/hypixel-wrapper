package net.dungeonhub.hypixel.entities.inventory.items

interface GearFromOphelia : SkyblockItemFactory {
    val requiredFloorCompletion: Int?
        get() = extraAttributes.getInt("shop_dungeon_floor_completion_required", -1).takeIf { it != -1 }
}