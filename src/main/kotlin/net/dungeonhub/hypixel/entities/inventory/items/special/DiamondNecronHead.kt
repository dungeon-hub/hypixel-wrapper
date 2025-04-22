package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Armor

class DiamondNecronHead(raw: NBTCompound) : Armor(raw) {
    val favoriteDiamondKnightSkin: Int?
        get() = extraAttributes.getInt("favorite_diamond_knight", -1).takeIf { it != -1 }
}