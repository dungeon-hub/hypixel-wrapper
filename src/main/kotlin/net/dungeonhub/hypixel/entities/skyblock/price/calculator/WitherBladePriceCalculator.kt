package net.dungeonhub.hypixel.entities.skyblock.price.calculator

import net.dungeonhub.hypixel.entities.inventory.items.id.KnownNecronsScroll
import net.dungeonhub.hypixel.entities.inventory.items.special.WitherBlade
import net.dungeonhub.hypixel.entities.skyblock.price.PriceHelper

object WitherBladePriceCalculator : PriceCalculator<WitherBlade> {
    override val type = WitherBlade::class.java

    override fun calculatePrice(item: WitherBlade): Long {
        var price = 0L

        for (scroll in item.necronsScrolls) {
            if (scroll is KnownNecronsScroll) {
                price += PriceHelper.getAppliedPrice(scroll)
            }
        }

        return price
    }
}