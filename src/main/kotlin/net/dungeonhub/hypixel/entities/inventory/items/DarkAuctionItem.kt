package net.dungeonhub.hypixel.entities.inventory.items

interface DarkAuctionItem : SkyblockItemFactory {
    val winningBid: Int
        get() = extraAttributes.getInt("winning_bid", 0)
}