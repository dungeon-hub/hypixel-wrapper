package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.DarkAuctionItem
import net.dungeonhub.hypixel.entities.inventory.items.Sword

class MidasWeapon(raw: NBTCompound) : Sword(raw), DarkAuctionItem {
    val gildedCoins: Int
        get() = extraAttributes.getInt("gilded_gifted_coins", 0)

    val additionalCoins: Int
        get() = extraAttributes.getInt("additional_coins", 0)

    val pricePaid: Int
        get() = winningBid + additionalCoins
}