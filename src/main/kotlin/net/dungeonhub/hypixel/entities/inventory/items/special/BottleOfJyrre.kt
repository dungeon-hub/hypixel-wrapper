package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem
import java.time.Instant

class BottleOfJyrre(raw: NBTCompound) : SkyblockItem(raw) {
    val secondsHeld: Int
        get() = extraAttributes.getInt("bottle_of_jyrre_seconds", 0)

    val lastUpdated: Instant?
        get() = extraAttributes.getLong("bottle_of_jyrre_last_update", -1L).takeIf { it != -1L }
            ?.let { Instant.ofEpochMilli(it) }
}