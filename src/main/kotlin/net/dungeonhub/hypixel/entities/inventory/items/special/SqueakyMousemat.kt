package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

class SqueakyMousemat(raw: NBTCompound) : SkyblockItem(raw) {
    val yaw: Float
        get() = extraAttributes.getFloat("mousemat_yaw", 0F)

    val pitch: Float
        get() = extraAttributes.getFloat("mousemat_pitch", 0F)
}