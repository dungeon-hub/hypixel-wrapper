package net.dungeonhub.hypixel.entities.skyblock.price.calculator

import net.dungeonhub.hypixel.entities.inventory.items.ManaDisintegratable
import net.dungeonhub.hypixel.entities.inventory.items.id.MiscItemId
import net.dungeonhub.hypixel.entities.skyblock.price.PriceHelper

object ManaDisintegratablePriceCalculator : PriceCalculator<ManaDisintegratable> {
    override val type = ManaDisintegratable::class.java

    override fun calculatePrice(item: ManaDisintegratable): Long {
        if (item.manaDisintegrators == 0) return 0L

        return PriceHelper.getAppliedPrice(MiscItemId.ManaDisintegrator) * item.manaDisintegrators
    }
}