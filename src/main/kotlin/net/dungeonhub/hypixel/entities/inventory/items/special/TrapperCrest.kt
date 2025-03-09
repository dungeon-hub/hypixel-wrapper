package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Accessory

class TrapperCrest(raw: NBTCompound) : Accessory(raw) {
    val pelts: Int
        get() = extraAttributes.getInt("pelts_earned", 0)
}