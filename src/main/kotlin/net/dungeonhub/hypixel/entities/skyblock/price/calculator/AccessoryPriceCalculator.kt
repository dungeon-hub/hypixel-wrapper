package net.dungeonhub.hypixel.entities.skyblock.price.calculator

import net.dungeonhub.hypixel.entities.inventory.items.Accessory
import net.dungeonhub.hypixel.entities.inventory.items.id.KnownEnrichmentId
import net.dungeonhub.hypixel.entities.skyblock.price.PriceHelper

object AccessoryPriceCalculator : PriceCalculator<Accessory> {
    override val type = Accessory::class.java

    override fun calculatePrice(item: Accessory): Long {
        val enrichment = item.enrichment

        if (enrichment is KnownEnrichmentId) {
            return PriceHelper.getAppliedPrice(enrichment)
        }

        return 0
    }
}