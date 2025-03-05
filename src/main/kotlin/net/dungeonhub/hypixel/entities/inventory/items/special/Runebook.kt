package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Accessory

// Note: The following fields were excluded, as they sometimes appear but aren't important at all:
// raffle_win, raffle_year, giftbag_claim
class Runebook(raw: NBTCompound) : Accessory(raw) {
    val runicKills: Int
        get() = extraAttributes.getInt("runic_kills", 0)
}