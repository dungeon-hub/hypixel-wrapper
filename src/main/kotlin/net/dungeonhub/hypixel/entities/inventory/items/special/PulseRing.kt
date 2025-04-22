package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Accessory

class PulseRing(raw: NBTCompound) : Accessory(raw) {
    val thunderCharge: Int
        get() = extraAttributes.getInt("thunder_charge", 0)
}