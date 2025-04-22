package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import java.time.Instant

class BottleOfJyrre(raw: NBTCompound) : TimeBagItem(raw) {
    override val secondsHeld: Int
        get() = extraAttributes.getInt("bottle_of_jyrre_seconds", 0)

    val maxedStats: Boolean
        get() = extraAttributes.getInt("maxed_stats", 0) == 1

    val lastUpdated: Instant?
        get() = extraAttributes.getLong("bottle_of_jyrre_last_update", -1L).takeIf { it != -1L }
            ?.let { Instant.ofEpochMilli(it) }
}