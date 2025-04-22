package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

class NewYearCake(raw: NBTCompound) : SkyblockItem(raw) {
    val year: Int
        get() = extraAttributes.getInt("new_years_cake", -1)
}