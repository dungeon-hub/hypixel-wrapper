package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Accessory

class BloodGodCrest(raw: NBTCompound) : Accessory(raw) {
    val kills: Int
        get() = extraAttributes.getInt("blood_god_kills", 0)
}