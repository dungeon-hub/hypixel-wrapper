package net.dungeonhub.hypixel.entities.skyblock.price.calculator

import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem
import net.dungeonhub.hypixel.entities.inventory.items.id.DungeonItemId
import net.dungeonhub.hypixel.entities.skyblock.price.PriceHelper

object SkyblockItemPriceCalculator : PriceCalculator<SkyblockItem> {
    override val type = SkyblockItem::class.java

    override fun calculatePrice(item: SkyblockItem): Long {
        return if (item.rarityUpgraded)
            PriceHelper.getAppliedPrice(DungeonItemId.Recombobulator3000)
        else 0
    }
}