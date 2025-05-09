package net.dungeonhub.hypixel.entities.skyblock.price.calculator

import net.dungeonhub.hypixel.entities.inventory.items.PowderCoatableItem
import net.dungeonhub.hypixel.entities.inventory.items.id.ForgeableItemId
import net.dungeonhub.hypixel.entities.skyblock.price.PriceHelper

object PowderCoatableItemPriceCalculator : PriceCalculator<PowderCoatableItem> {
    override val type = PowderCoatableItem::class.java

    override fun calculatePrice(item: PowderCoatableItem): Long {
        if (item.powderCoating) {
            return PriceHelper.getAppliedPrice(ForgeableItemId.DivansPowderCoating)
        }

        return 0
    }
}