package net.dungeonhub.hypixel.entities.skyblock.price.calculator

import net.dungeonhub.hypixel.entities.inventory.items.id.ForgeableItemId
import net.dungeonhub.hypixel.entities.inventory.items.special.ArmorOfDivan
import net.dungeonhub.hypixel.entities.skyblock.price.PriceHelper

object ArmorOfDivanPriceCalculator : PriceCalculator<ArmorOfDivan> {
    override val type = ArmorOfDivan::class.java

    override fun calculatePrice(item: ArmorOfDivan): Long {
        if (item.gemstoneSlots > 0) {
            return PriceHelper.getAppliedPrice(ForgeableItemId.GemstoneChamber) * item.gemstoneSlots
        }

        return 0
    }
}