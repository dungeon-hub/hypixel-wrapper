package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

class NameTag(raw: NBTCompound) : SkyblockItem(raw) {
    val nameTagName: String?
        get() = extraAttributes.getString("name_tag")
}