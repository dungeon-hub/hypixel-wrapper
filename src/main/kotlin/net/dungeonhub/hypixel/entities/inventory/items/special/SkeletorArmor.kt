package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Armor

class SkeletorArmor(raw: NBTCompound) : Armor(raw) {
    val skeletorKills: Int
        get() = extraAttributes.getInt("skeletorKills", 0)
}