package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Sword

class FelSword(raw: NBTCompound) : Sword(raw) {
    val kills: Int
        get() = extraAttributes.getInt("sword_kills", 0)
}