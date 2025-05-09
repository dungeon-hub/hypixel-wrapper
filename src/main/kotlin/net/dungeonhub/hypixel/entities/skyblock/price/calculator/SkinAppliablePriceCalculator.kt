package net.dungeonhub.hypixel.entities.skyblock.price.calculator

import net.dungeonhub.hypixel.entities.inventory.items.SkinAppliable
import net.dungeonhub.hypixel.entities.inventory.items.id.KnownDyeId
import net.dungeonhub.hypixel.entities.skyblock.price.PriceHelper

object SkinAppliablePriceCalculator : PriceCalculator<SkinAppliable> {
    override val type = SkinAppliable::class.java

    override fun calculatePrice(item: SkinAppliable): Long {
        var price = 0L

        val appliedSkin = item.appliedSkin
        if (appliedSkin != null) {
            //TODO implement
        }

        val appliedDye = item.appliedDye
        if (appliedDye is KnownDyeId) {
            price += PriceHelper.getAppliedPrice(appliedDye)
        }

        return price
    }
}