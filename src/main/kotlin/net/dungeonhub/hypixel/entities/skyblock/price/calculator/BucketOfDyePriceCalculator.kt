package net.dungeonhub.hypixel.entities.skyblock.price.calculator

import net.dungeonhub.hypixel.entities.inventory.items.id.KnownDyeId
import net.dungeonhub.hypixel.entities.inventory.items.special.BucketOfDye
import net.dungeonhub.hypixel.entities.skyblock.price.PriceHelper

object BucketOfDyePriceCalculator : PriceCalculator<BucketOfDye> {
    override val type = BucketOfDye::class.java

    override fun calculatePrice(item: BucketOfDye): Long {
        val dye = item.donatedDye
        if (dye != null && dye is KnownDyeId) {
            return PriceHelper.getAppliedPrice(dye)
        }

        return 0
    }
}