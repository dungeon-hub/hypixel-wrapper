package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Armor

class RanchersBoots(raw: NBTCompound) : Armor(raw) {
    val speed: Int?
        get() = extraAttributes.getInt("ranchers_speed", -1).takeIf { it != -1 }
}