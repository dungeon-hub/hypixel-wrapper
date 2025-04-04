package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

open class TimeBagItem(raw: NBTCompound) : SkyblockItem(raw) {
    open val secondsHeld: Int
        get() = extraAttributes.getInt("seconds_held", 0)
}