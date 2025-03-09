package net.dungeonhub.hypixel.entities.inventory.items

import me.nullicorn.nedit.type.NBTCompound

//TODO check fields
class Armor(raw: NBTCompound) : Gear(raw), ItemWithHotPotatoBooks, SkinAppliable, ItemFromBoss, DungeonItem {
    val artOfPiece: Boolean
        get() = extraAttributes.getInt("artOfPeaceApplied", 0) == 1

    val color: String //TODO can this be parsed to an actual java color? format is: 0:0:0
        get() = extraAttributes.getString("color")
}