package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Armor

class TarantulaArmor(raw: NBTCompound) : Armor(raw) {
    val spiderKills: Int
        get() = extraAttributes.getInt("spider_kills", 0)
}