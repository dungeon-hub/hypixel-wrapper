package net.dungeonhub.hypixel.entities.inventory.items

interface ItemWithHotPotatoBooks : SkyblockItemFactory {
    val hotPotatoes: Int
        get() = extraAttributes.getInt("hot_potato_count", 0)

    /**
     * Note that this isn't populated for all items with hot potato books, only some have this.
     */
    val hotPotatoBonus: Map<String, Int>
        get() = extraAttributes.getString("hotPotatoBonus")?.split(";")?.filter { it.isNotBlank() }
            ?.associate { it.split("=")[0] to it.split("=")[1].toInt() } ?: emptyMap()
}