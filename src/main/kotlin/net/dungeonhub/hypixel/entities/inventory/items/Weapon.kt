package net.dungeonhub.hypixel.entities.inventory.items

import me.nullicorn.nedit.type.NBTCompound

//TODO check fields
open class Weapon(raw: NBTCompound) : Gear(raw), ItemWithHotPotatoBooks, ItemFromBoss, DungeonItem {
    val hasArtOfWar: Boolean
        get() = extraAttributes.getInt("art_of_war_count", 0) > 0
}