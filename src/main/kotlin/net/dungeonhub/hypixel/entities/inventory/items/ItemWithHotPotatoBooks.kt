package net.dungeonhub.hypixel.entities.inventory.items

interface ItemWithHotPotatoBooks: SkyblockItemFactory {
    val hotPotatoes: Int
        get() = extraAttributes.getInt("hot_potato_count", 0)
}