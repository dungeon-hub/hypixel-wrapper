package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Gear

class VanquishedMagmaNecklace(raw: NBTCompound) : Gear(raw) {
    val magmaCubesKilled: Int
        get() = extraAttributes.getInt("magma_cube_absorber", 0)
}