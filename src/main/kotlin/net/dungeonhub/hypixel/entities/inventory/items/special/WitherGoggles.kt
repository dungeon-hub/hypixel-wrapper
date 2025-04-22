package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Armor

class WitherGoggles(raw: NBTCompound) : Armor(raw) {
    /**
     * This probably corresponds to the selection of the `Corrupt Wither Goggles` skin.
     */
    val favoriteEvilSkin: Int?
        get() = extraAttributes.getInt("favorite_evil_skin", -1).takeIf { it != -1 }
}