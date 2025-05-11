package net.dungeonhub.hypixel.entities.skyblock.price.calculator

import net.dungeonhub.hypixel.entities.inventory.items.id.MiscItemId
import net.dungeonhub.hypixel.entities.inventory.items.special.PestVacuum
import net.dungeonhub.hypixel.entities.skyblock.price.PriceHelper

object PestVacuumPriceCalculator : PriceCalculator<PestVacuum> {
    override val type = PestVacuum::class.java

    override fun calculatePrice(item: PestVacuum): Long {
        if (item.bookwormBooks > 0) {
            return PriceHelper.getAppliedPrice(MiscItemId.BookwormsFavoriteBook) * item.bookwormBooks
        }

        return 0
    }
}