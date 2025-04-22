package net.dungeonhub.hypixel.entities.inventory.items

interface ItemWithGreatSpookYear : SkyblockItemFactory {
    val year: Int
        get() = extraAttributes.getInt("year", 0)
}