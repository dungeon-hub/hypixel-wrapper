package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Armor

class FermentoHelmet(raw: NBTCompound) : Armor(raw) {
    val favoriteCropSkin: Int?
        get() = extraAttributes.getInt("favorite_crop", -1).takeIf { it != -1 }
}