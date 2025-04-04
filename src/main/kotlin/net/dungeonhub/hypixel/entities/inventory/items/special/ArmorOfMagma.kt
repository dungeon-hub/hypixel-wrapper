package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Armor

class ArmorOfMagma(raw: NBTCompound) : Armor(raw) {
    val magmaCubesKilled: Int
        get() = extraAttributes.getInt("magmaCubesKilled", 0)
}