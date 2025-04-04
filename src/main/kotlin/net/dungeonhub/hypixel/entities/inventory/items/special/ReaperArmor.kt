package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Armor

class ReaperArmor(raw: NBTCompound) : Armor(raw) {
    val zombieKills: Int
        get() = extraAttributes.getInt("zombie_kills", 0)
}