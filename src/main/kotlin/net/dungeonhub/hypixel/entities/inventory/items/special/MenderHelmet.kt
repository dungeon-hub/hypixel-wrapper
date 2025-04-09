package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Armor

class MenderHelmet(raw: NBTCompound) : Armor(raw) {
    val favoriteSnakeSkin: Int?
        get() = extraAttributes.getInt("favorite_snake", -1).takeIf { it != -1 }
}