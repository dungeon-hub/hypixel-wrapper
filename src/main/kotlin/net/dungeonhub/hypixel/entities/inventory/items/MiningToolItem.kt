package net.dungeonhub.hypixel.entities.inventory.items

interface MiningToolItem : SkyblockItemFactory {
    val compactBlocks: Int
        get() = extraAttributes.getInt("compact_blocks", 0)
}