package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Accessory

class Runebook(raw: NBTCompound) : Accessory(raw) {
    val runicKills: Int
        get() = extraAttributes.getInt("runic_kills", 0)
}