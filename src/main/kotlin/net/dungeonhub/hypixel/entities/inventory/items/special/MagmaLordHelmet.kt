package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Armor

class MagmaLordHelmet(raw: NBTCompound) : Armor(raw) {
    val favoriteSharkSkin: Int?
        get() = extraAttributes.getInt("favorite_shark", -1).takeIf { it != -1 }
}