package net.dungeonhub.hypixel.entities.inventory.items

import me.nullicorn.nedit.type.NBTCompound

//TODO check fields
class RaidersAxe(raw: NBTCompound) : Weapon(raw) {
    val raiderKills: Int
        get() = extraAttributes.getInt("raider_kills", 0)
}