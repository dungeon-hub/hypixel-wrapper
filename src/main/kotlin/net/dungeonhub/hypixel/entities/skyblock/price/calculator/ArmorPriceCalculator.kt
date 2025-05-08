package net.dungeonhub.hypixel.entities.skyblock.price.calculator

import net.dungeonhub.hypixel.entities.inventory.items.Armor
import net.dungeonhub.hypixel.entities.inventory.items.id.MiscItemId
import net.dungeonhub.hypixel.entities.skyblock.price.PriceHelper

object ArmorPriceCalculator : PriceCalculator<Armor> {
    override val type = Armor::class.java

    override fun calculatePrice(item: Armor): Long {
        if (item.artOfPeace) {
            return PriceHelper.getAppliedPrice(MiscItemId.TheArtOfPeace)
        }

        return 0
    }
}