package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Armor

class ArmorOfGrowth(raw: NBTCompound) : Armor(raw) {
    val bonusHealth: Int
        get() = extraAttributes.getInt("health", 0)
}