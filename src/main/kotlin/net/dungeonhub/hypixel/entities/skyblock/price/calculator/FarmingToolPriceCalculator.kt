package net.dungeonhub.hypixel.entities.skyblock.price.calculator

import net.dungeonhub.hypixel.entities.inventory.items.id.MiscItemId
import net.dungeonhub.hypixel.entities.inventory.items.special.FarmingTool
import net.dungeonhub.hypixel.entities.skyblock.price.PriceHelper

object FarmingToolPriceCalculator : PriceCalculator<FarmingTool> {
    override val type = FarmingTool::class.java

    override fun calculatePrice(item: FarmingTool): Long {
        if (item.farmingForDummies > 0) {
            return PriceHelper.getAppliedPrice(MiscItemId.FarmingForDummies) * item.farmingForDummies
        }

        return 0
    }
}