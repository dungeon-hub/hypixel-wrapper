package net.dungeonhub.hypixel.entities.skyblock.price.calculator

import net.dungeonhub.hypixel.entities.inventory.items.id.MiscItemId
import net.dungeonhub.hypixel.entities.inventory.items.id.RiftItemId
import net.dungeonhub.hypixel.entities.inventory.items.special.Minion
import net.dungeonhub.hypixel.entities.skyblock.price.PriceHelper

object MinionPriceCalculator : PriceCalculator<Minion> {
    override val type = Minion::class.java

    override fun calculatePrice(item: Minion): Long {
        var price = 0L

        if (item.mithrilInfused) {
            //TODO check application worth
            price += PriceHelper.getAppliedPrice(MiscItemId.MithrilInfusion)
        }

        if (item.freeWill) {
            //TODO check application worth
            price += PriceHelper.getAppliedPrice(RiftItemId.FreeWill)
        }

        return price
    }
}