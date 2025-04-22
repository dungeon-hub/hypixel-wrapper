package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Armor

class FlexHelmet(raw: NBTCompound) : Armor(raw) {
    val selectedSkin: Int?
        get() = extraAttributes.getInt("fh_selected_skin", -1).takeIf { it != -1 }

    val secondsWorn: Int
        get() = extraAttributes.getInt("fh_seconds_worn", 0)

    val secondsWornRift: Int
        get() = extraAttributes.getInt("fh_seconds_worn_rift", 0)
}