package net.dungeonhub.hypixel.entities.inventory.items

//TODO map reforge data to enum
interface ReforgeableItem: SkyblockItemFactory {
    val reforge: String?
        get() = extraAttributes.getString("modifier")
}