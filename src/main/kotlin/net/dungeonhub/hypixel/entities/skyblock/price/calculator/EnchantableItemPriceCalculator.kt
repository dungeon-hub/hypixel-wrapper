package net.dungeonhub.hypixel.entities.skyblock.price.calculator

import net.dungeonhub.hypixel.entities.inventory.items.EnchantableItem
import net.dungeonhub.hypixel.entities.inventory.items.Enchantment
import net.dungeonhub.hypixel.entities.inventory.items.id.MiscItemId
import net.dungeonhub.hypixel.entities.inventory.items.special.EnchantedBook
import net.dungeonhub.hypixel.entities.skyblock.price.PriceHelper
import kotlin.math.roundToLong

object EnchantableItemPriceCalculator : PriceCalculator<EnchantableItem> {
    override val type = EnchantableItem::class.java

    override fun calculatePrice(item: EnchantableItem): Long {
        val isEnchantedBook = item is EnchantedBook

        var price = 0L

        for ((enchantment, level) in item.enchantments) {
            price += (getEnchantmentPrice(
                enchantment,
                level
            ) * (if (isEnchantedBook) 1.0 else PriceHelper.getApplicationWorth(MiscItemId.EnchantedBook))).roundToLong()
        }

        return price
    }

    fun getEnchantmentPrice(enchantment: Enchantment, level: Int): Long {
        //TODO implement
        return 0
    }
}