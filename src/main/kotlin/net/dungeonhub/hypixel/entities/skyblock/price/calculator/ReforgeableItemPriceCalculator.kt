package net.dungeonhub.hypixel.entities.skyblock.price.calculator

import net.dungeonhub.hypixel.entities.inventory.items.ReforgeableItem

object ReforgeableItemPriceCalculator : PriceCalculator<ReforgeableItem> {
    override val type = ReforgeableItem::class.java

    override fun calculatePrice(item: ReforgeableItem): Long {
        //TODO implement
        return 0
    }
}