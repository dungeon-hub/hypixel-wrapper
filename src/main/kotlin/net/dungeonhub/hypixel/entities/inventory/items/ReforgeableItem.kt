package net.dungeonhub.hypixel.entities.inventory.items

interface ReforgeableItem: SkyblockItemFactory {
    val reforge: String?
        get() = extraAttributes.getString("modifier")
}