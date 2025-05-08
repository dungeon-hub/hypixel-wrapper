package net.dungeonhub.hypixel.entities.skyblock.price.calculator

import net.dungeonhub.hypixel.entities.inventory.items.FishingRod
import net.dungeonhub.hypixel.entities.inventory.items.id.KnownRodPartId
import net.dungeonhub.hypixel.entities.skyblock.price.PriceHelper

object FishingRodPriceCalculator : PriceCalculator<FishingRod> {
    override val type = FishingRod::class.java

    override fun calculatePrice(item: FishingRod): Long {
        var price = 0L

        val sinker = item.sinker?.part as? KnownRodPartId
        if (sinker != null) {
            price += PriceHelper.getAppliedPrice(sinker)
        }

        val line = item.line?.part as? KnownRodPartId
        if (line != null) {
            price += PriceHelper.getAppliedPrice(line)
        }

        val hook = item.hook?.part as? KnownRodPartId
        if (hook != null) {
            price += PriceHelper.getAppliedPrice(hook)
        }

        return price
    }
}