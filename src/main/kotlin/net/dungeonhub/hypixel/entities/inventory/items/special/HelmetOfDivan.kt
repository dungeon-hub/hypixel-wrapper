package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound

class HelmetOfDivan(raw: NBTCompound) : ArmorOfDivan(raw) {
    val favoriteGemstoneSkin: Int?
        get() = extraAttributes.getInt("favorite_gemstone", -1).takeIf { it != -1 }
}