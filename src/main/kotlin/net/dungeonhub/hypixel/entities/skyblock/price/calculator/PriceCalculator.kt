package net.dungeonhub.hypixel.entities.skyblock.price.calculator

import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItemFactory

interface PriceCalculator<T : SkyblockItemFactory> : BasePriceCalculator {
    override fun calculateBasePrice(item: SkyblockItemFactory): Long {
        @Suppress("UNCHECKED_CAST")
        return (item as? T)?.let(this::calculatePrice) ?: 0
    }

    override fun appliesToItem(item: SkyblockItemFactory): Boolean {
        //TODO test
        return type.isAssignableFrom(item.javaClass)
    }

    val type: Class<T>

    fun calculatePrice(item: T): Long
}