package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

class CakeSlice(raw: NBTCompound) : SkyblockItem(raw) {
    val centuryYearObtained: Int?
        get() = extraAttributes.getInt("century_year_obtained", -1).takeIf { it != -1 }
}