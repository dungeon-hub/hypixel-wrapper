package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound

class Pickonimbus(raw: NBTCompound) : MiningTool(raw) {
    val durability: Int
        get() = extraAttributes.getInt("pickonimbus_durability", 2000)
}