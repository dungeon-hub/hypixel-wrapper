package net.dungeonhub.hypixel.entities.inventory.items

interface DungeonItem : SkyblockItemFactory {
    val dungeonLevel: Int
        get() = extraAttributes.getInt("dungeon_item_level", 0)
}