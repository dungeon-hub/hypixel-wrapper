package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem
import java.time.Instant

open class TimeBagItem(raw: NBTCompound) : SkyblockItem(raw) {
    open val secondsHeld: Int
        get() = extraAttributes.getInt("seconds_held", 0)

    val lastForceEvolvedTime: Instant?
        get() = extraAttributes.getLong("lastForceEvolvedTime", -1).takeIf { it != -1L }
            ?.let { Instant.ofEpochMilli(it) }
}