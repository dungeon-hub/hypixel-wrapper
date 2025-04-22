package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

class DittoSkull(raw: NBTCompound) : SkyblockItem(raw) {
    val skullValue: String?
        get() = extraAttributes.getString("skullValue")
}