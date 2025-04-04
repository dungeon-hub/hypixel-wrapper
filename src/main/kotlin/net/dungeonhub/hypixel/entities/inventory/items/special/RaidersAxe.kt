package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Sword

class RaidersAxe(raw: NBTCompound) : Sword(raw) {
    val raiderKills: Int
        get() = extraAttributes.getInt("raider_kills", 0)
}