package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

class CapsaicinEyedropsOld(raw: NBTCompound) : SkyblockItem(raw) {
    val chargesUsed: Int
        get() = extraAttributes.getInt("charges_used", 0)
}