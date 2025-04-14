package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Armor

class TikiMask(raw: NBTCompound) : Armor(raw) {
    val tikiSkin: Int?
        get() = extraAttributes.getInt("tiki_skin", -1).takeIf { it != -1 }
}