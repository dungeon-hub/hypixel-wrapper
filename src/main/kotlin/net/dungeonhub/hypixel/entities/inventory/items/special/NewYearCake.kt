package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

class NewYearCake(raw: NBTCompound) : SkyblockItem(raw) {
    override val uniqueName: String
        get() {
            return if (year != -1) {
                "NEW_YEAR_CAKE_$year"
            } else {
                super.uniqueName
            }
        }

    val year: Int
        get() = extraAttributes.getInt("new_years_cake", -1)
}