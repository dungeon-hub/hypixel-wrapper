package net.dungeonhub.hypixel.entities.inventory.items

interface ManaDisintegratable : SkyblockItemFactory {
    val manaDisintegrators: Int
        get() = extraAttributes.getInt("mana_disintegrator_count", 0)
}