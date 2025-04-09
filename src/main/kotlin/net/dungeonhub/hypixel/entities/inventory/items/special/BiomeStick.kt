package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

class BiomeStick(raw: NBTCompound) : SkyblockItem(raw) {
    val radius: Int?
        get() = extraAttributes.getInt("radius", -1).takeIf { it != -1 }
}