package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

class LoudmouthBass(raw: NBTCompound) : SkyblockItem(raw) {
    val weight: Int?
        get() = extraAttributes.getInt("bass_weight", -1).takeIf { it != -1 }
}