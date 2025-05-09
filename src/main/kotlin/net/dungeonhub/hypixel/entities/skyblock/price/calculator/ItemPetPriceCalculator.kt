package net.dungeonhub.hypixel.entities.skyblock.price.calculator

import net.dungeonhub.hypixel.entities.inventory.items.PetAsItem

object ItemPetPriceCalculator : PriceCalculator<PetAsItem> {
    override val type = PetAsItem::class.java

    override fun calculatePrice(item: PetAsItem): Long {
        //TODO implement
        return 0
    }
}