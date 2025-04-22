package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Bow

class HurricaneBow(raw: NBTCompound) : Bow(raw) {
    val bowKills: Int
        get() = extraAttributes.getInt("bow_kills", 0)
}