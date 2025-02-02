package net.dungeonhub.hypixel.entities.inventory.items

import me.nullicorn.nedit.type.NBTCompound

open class Weapon(raw: NBTCompound) : Gear(raw), ItemWithHotPotatoBooks {
    val hasArtOfWar: Boolean
        get() = extraAttributes.getInt("art_of_war_count", 0) > 0
}