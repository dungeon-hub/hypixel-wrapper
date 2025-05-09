package net.dungeonhub.hypixel.entities.skyblock.price.calculator

import net.dungeonhub.hypixel.entities.inventory.items.Sword
import net.dungeonhub.hypixel.entities.inventory.items.id.MiscItemId
import net.dungeonhub.hypixel.entities.skyblock.price.PriceHelper

object SwordPriceCalculator : PriceCalculator<Sword> {
    override val type = Sword::class.java

    override fun calculatePrice(item: Sword): Long {
        if (item.woodSingularity) {
            return PriceHelper.getAppliedPrice(MiscItemId.WoodSingularity)
        }

        return 0
    }
}