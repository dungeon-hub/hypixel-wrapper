package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Sword

class RecluseFang(raw: NBTCompound) : Sword(raw) {
    val spiderKills: Int
        get() = extraAttributes.getInt("spider_kills", 0)
}