package net.dungeonhub.hypixel.entities.skyblock.price.calculator

import net.dungeonhub.hypixel.entities.inventory.items.TeleportationSword
import net.dungeonhub.hypixel.entities.inventory.items.id.MiscItemId
import net.dungeonhub.hypixel.entities.skyblock.price.PriceHelper

object TeleportationSwordPriceCalculator : PriceCalculator<TeleportationSword> {
    override val type = TeleportationSword::class.java

    override fun calculatePrice(item: TeleportationSword): Long {
        var price = 0L

        if (item.etherWarp) {
            price += PriceHelper.getAppliedPrice(MiscItemId.EtherwarpConduit)
            price += PriceHelper.getAppliedPrice(MiscItemId.EtherwarpMerger)
        }

        if (item.transmissionTuners > 0) {
            price += PriceHelper.getAppliedPrice(MiscItemId.TransmissionTuner) * item.transmissionTuners
        }

        return price
    }
}