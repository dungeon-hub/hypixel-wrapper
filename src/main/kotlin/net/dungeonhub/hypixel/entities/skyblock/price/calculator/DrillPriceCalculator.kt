package net.dungeonhub.hypixel.entities.skyblock.price.calculator

import net.dungeonhub.hypixel.entities.inventory.items.id.RiftItemId
import net.dungeonhub.hypixel.entities.inventory.items.special.Drill
import net.dungeonhub.hypixel.entities.skyblock.price.PriceHelper

object DrillPriceCalculator : PriceCalculator<Drill> {
    override val type = Drill::class.java

    override fun calculatePrice(item: Drill): Long {
        var price = 0L

        if (item.polarvoid > 0) {
            price += PriceHelper.getAppliedPrice(RiftItemId.PolarvoidBook) * item.polarvoid
        }

        if (item.upgradeModule != null) {
            price += item.upgradeModule?.let(PriceHelper::getAppliedPrice) ?: 0
        }

        if (item.engine != null) {
            price += item.engine?.let(PriceHelper::getAppliedPrice) ?: 0
        }

        if (item.fuelTank != null) {
            price += item.fuelTank?.let(PriceHelper::getAppliedPrice) ?: 0
        }

        return price
    }
}