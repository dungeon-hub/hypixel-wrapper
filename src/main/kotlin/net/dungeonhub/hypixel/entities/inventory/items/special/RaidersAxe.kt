package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Weapon

//TODO check fields
class RaidersAxe(raw: NBTCompound) : Weapon(raw) {
    val raiderKills: Int
        get() = extraAttributes.getInt("raider_kills", 0)
}