package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Gear

class Pickonimbus(raw: NBTCompound) : Gear(raw) {
    val durability: Int
        get() = extraAttributes.getInt("pickonimbus_durability", 2000)
}