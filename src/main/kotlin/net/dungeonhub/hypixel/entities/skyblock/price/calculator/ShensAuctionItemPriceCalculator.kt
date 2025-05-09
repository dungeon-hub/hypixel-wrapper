package net.dungeonhub.hypixel.entities.skyblock.price.calculator

import net.dungeonhub.hypixel.entities.inventory.items.ShensAuctionItem
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem
import net.dungeonhub.hypixel.entities.skyblock.price.PriceHelper
import kotlin.math.roundToLong

object ShensAuctionItemPriceCalculator : PriceCalculator<ShensAuctionItem> {
    private const val SHENS_AUCTION_PRICE_VALUE = 0.85

    override val type = ShensAuctionItem::class.java

    override fun calculatePrice(item: ShensAuctionItem): Long {
        val pricePaid = ((item.pricePaid ?: 0) * SHENS_AUCTION_PRICE_VALUE).roundToLong()

        val baseItemPrice = (item as? SkyblockItem)?.id?.let(PriceHelper::getBasePrice) ?: 0

        if (pricePaid > baseItemPrice) {
            return pricePaid - baseItemPrice
        }

        return 0
    }
}