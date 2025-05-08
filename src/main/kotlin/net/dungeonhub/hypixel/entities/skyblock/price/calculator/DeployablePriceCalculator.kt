package net.dungeonhub.hypixel.entities.skyblock.price.calculator

import net.dungeonhub.hypixel.entities.inventory.items.Deployable
import net.dungeonhub.hypixel.entities.inventory.items.id.MiscItemId
import net.dungeonhub.hypixel.entities.skyblock.price.PriceHelper

object DeployablePriceCalculator : PriceCalculator<Deployable> {
    override val type = Deployable::class.java

    override fun calculatePrice(item: Deployable): Long {
        if (item.hasJalapeno) {
            return PriceHelper.getAppliedPrice(MiscItemId.JalapenoBook)
        }

        return 0
    }
}