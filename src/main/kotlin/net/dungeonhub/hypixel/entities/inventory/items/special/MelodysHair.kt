package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Accessory

class MelodysHair(raw: NBTCompound) : Accessory(raw) {
    val tune: Int
        get() = extraAttributes.getInt("tune", 0)
}