package net.dungeonhub.hypixel.entities.skyblock.price.calculator

import net.dungeonhub.hypixel.entities.inventory.items.ItemWithCombatBooks
import net.dungeonhub.hypixel.entities.inventory.items.id.CosmeticItemId
import net.dungeonhub.hypixel.entities.inventory.items.id.MiscItemId
import net.dungeonhub.hypixel.entities.skyblock.price.PriceHelper

object CombatBookItemPriceCalculator : PriceCalculator<ItemWithCombatBooks> {
    override val type = ItemWithCombatBooks::class.java

    override fun calculatePrice(item: ItemWithCombatBooks): Long {
        var price = 0L

        if (item.hasArtOfWar) {
            price += PriceHelper.getAppliedPrice(MiscItemId.TheArtOfWar)
        }

        val statsBook = item.statsBook
        if (statsBook != null && statsBook > 0) {
            price += PriceHelper.getAppliedPrice(CosmeticItemId.BookOfStats)
        }

        return price
    }
}