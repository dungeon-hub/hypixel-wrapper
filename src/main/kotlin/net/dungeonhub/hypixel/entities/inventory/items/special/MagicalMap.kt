package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

class MagicalMap(raw: NBTCompound) : SkyblockItem(raw) {
    val dontUpdateStack: Boolean
        get() = extraAttributes.getInt("dontUpdateStack", 0) == 1

    val dontSaveToProfile: Boolean
        get() = extraAttributes.getInt("dontSaveToProfile", 0) == 1
}