package net.dungeonhub.hypixel.entities.skyblock.price.calculator

import net.dungeonhub.hypixel.entities.inventory.items.ItemWithRune
import net.dungeonhub.hypixel.entities.inventory.items.id.CosmeticItemId
import net.dungeonhub.hypixel.entities.inventory.items.special.RuneItem
import net.dungeonhub.hypixel.entities.skyblock.price.PriceHelper
import kotlin.math.roundToLong

object RuneItemPriceCalculator : PriceCalculator<ItemWithRune> {
    override val type = ItemWithRune::class.java

    override fun calculatePrice(item: ItemWithRune): Long {
        if (listOf(CosmeticItemId.Rune, CosmeticItemId.UniqueRune).contains(item.id)) return 0

        var price = 0L

        for ((type, level) in item.runes) {
            val runeName = RuneItem.toRuneUniqueName(type, level)

            price += (PriceHelper.getBasePrice(runeName) * PriceHelper.getApplicationWorth(CosmeticItemId.Rune)).roundToLong()
        }

        return price
    }
}