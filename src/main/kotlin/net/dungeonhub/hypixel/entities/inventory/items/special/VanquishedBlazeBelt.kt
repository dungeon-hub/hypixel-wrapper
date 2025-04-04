package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Gear

class VanquishedBlazeBelt(raw: NBTCompound) : Gear(raw) {
    val blazedKilled: Int
        get() = extraAttributes.getInt("blaze_consumer", 0)
}