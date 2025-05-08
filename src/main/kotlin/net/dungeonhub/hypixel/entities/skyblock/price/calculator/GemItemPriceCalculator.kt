package net.dungeonhub.hypixel.entities.skyblock.price.calculator

import net.dungeonhub.hypixel.entities.inventory.items.ItemWithGems

object GemItemPriceCalculator : PriceCalculator<ItemWithGems> {
    override val type = ItemWithGems::class.java

    override fun calculatePrice(item: ItemWithGems): Long {
        val gems = item.gems

        if (gems != null) {
            //TODO calculate unlocked slot cost
            //TODO revamp gemstone slot system
        }

        return 0
    }
}