package net.dungeonhub.hypixel.entities.inventory.items

import java.time.Instant

interface ShensAuctionItem : SkyblockItemFactory {
    val pricePaid: Long?
        get() = extraAttributes.getLong("price", -1).takeIf { it != -1L }

    val bidNumber: Int?
        get() = extraAttributes.getInt("bid", -1).takeIf { it != -1 }

    val bidder: String?
        get() = extraAttributes.getString("player")

    val auctionNumber: Int?
        get() = extraAttributes.getInt("auction", -1).takeIf { it != -1 }

    val dateObtained: Instant?
        get() = extraAttributes.getLong("date", -1).takeIf { it != -1L }?.let { Instant.ofEpochMilli(it) }
}