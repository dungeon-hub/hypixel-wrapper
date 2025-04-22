package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.ShensAuctionItem

class WizardWand(raw: NBTCompound) : Wand(raw), ShensAuctionItem {
    val intelligenceEarned: Int
        get() = extraAttributes.getInt("intelligence_earned", 0)
}