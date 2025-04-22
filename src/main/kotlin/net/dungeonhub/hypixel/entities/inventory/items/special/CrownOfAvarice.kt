package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Armor

class CrownOfAvarice(raw: NBTCompound) : Armor(raw) {
    val collectedCoins: Int
        get() = extraAttributes.getInt("collected_coins", 0)
}