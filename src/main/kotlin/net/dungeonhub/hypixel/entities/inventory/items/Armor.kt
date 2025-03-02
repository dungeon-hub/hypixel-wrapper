package net.dungeonhub.hypixel.entities.inventory.items

import me.nullicorn.nedit.type.NBTCompound

//TODO check fields
class Armor(raw: NBTCompound) : Gear(raw), ItemWithHotPotatoBooks, SkinAppliable {
    val artOfPiece: Boolean
        get() = extraAttributes.getInt("artOfPeaceApplied", 0) == 1
}