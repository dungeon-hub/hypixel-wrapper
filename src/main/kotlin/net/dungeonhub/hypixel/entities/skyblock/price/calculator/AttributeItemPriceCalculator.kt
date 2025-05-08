package net.dungeonhub.hypixel.entities.skyblock.price.calculator

import net.dungeonhub.hypixel.entities.inventory.items.ItemWithAttributes

object AttributeItemPriceCalculator : PriceCalculator<ItemWithAttributes> {
    override val type = ItemWithAttributes::class.java

    override fun calculatePrice(item: ItemWithAttributes): Long {
        //TODO implement somehow
        return 0
    }
}