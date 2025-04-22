package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Armor

class WardenHelmet(raw: NBTCompound) : Armor(raw) {
    val favoriteSentinelWardenSkin: Int?
        get() = extraAttributes.getInt("favorite_sentinel_warden", -1).takeIf { it != -1 }
}