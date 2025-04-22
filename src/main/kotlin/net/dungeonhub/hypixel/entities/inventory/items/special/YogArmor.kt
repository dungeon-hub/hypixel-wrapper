package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Armor

class YogArmor(raw: NBTCompound) : Armor(raw) {
    val yogsKilled: Int
        get() = extraAttributes.getInt("yogsKilled", 0)
}