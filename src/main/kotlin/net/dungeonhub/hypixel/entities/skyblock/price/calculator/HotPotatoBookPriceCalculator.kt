package net.dungeonhub.hypixel.entities.skyblock.price.calculator

import net.dungeonhub.hypixel.entities.inventory.items.ItemWithHotPotatoBooks
import net.dungeonhub.hypixel.entities.inventory.items.id.DungeonItemId
import net.dungeonhub.hypixel.entities.inventory.items.id.MiscItemId
import net.dungeonhub.hypixel.entities.skyblock.price.PriceHelper
import kotlin.math.min

object HotPotatoBookPriceCalculator : PriceCalculator<ItemWithHotPotatoBooks> {
    override val type = ItemWithHotPotatoBooks::class.java

    override fun calculatePrice(item: ItemWithHotPotatoBooks): Long {
        var price = 0L

        if (item.hotPotatoes > 10) {
            price += PriceHelper.getAppliedPrice(DungeonItemId.FumingPotatoBook) * (item.hotPotatoes - 10)
        }

        price += PriceHelper.getAppliedPrice(MiscItemId.HotPotatoBook) * (min(item.hotPotatoes, 10))

        return price
    }
}