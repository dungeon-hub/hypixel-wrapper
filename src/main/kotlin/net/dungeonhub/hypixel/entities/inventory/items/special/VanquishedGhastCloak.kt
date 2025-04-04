package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Gear

class VanquishedGhastCloak(raw: NBTCompound) : Gear(raw) {
    val ghastsKilled: Int
        get() = extraAttributes.getInt("ghast_blaster", 0)
}