package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

class DefuseKit(raw: NBTCompound) : SkyblockItem(raw) {
    val trapsDefused: Int
        get() = extraAttributes.getInt("trapsDefused", 0)
}