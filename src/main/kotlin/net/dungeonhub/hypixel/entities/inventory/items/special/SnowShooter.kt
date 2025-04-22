package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

class SnowShooter(raw: NBTCompound) : SkyblockItem(raw) {
    val ammo: Int?
        get() = extraAttributes.getInt("ammo", -1).takeIf { it != -1 }
}