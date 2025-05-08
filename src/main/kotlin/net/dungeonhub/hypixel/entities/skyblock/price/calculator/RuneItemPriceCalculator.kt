package net.dungeonhub.hypixel.entities.skyblock.price.calculator

import net.dungeonhub.hypixel.entities.inventory.items.ItemWithRune

object RuneItemPriceCalculator : PriceCalculator<ItemWithRune> {
    override val type = ItemWithRune::class.java

    override fun calculatePrice(item: ItemWithRune): Long {
        //TODO implement

        return 0
    }
}