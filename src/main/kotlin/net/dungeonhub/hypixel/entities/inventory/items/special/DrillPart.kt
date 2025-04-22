package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

class DrillPart(raw: NBTCompound) : SkyblockItem(raw) {
    val storedFuel: Int
        get() = extraAttributes.getInt("stored_drill_fuel", 0)
}