package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

class Sack(raw: NBTCompound) : SkyblockItem(raw) {
    val pocketSackApplied: Int
        get() = extraAttributes.getInt("sack_pss", 0)
}