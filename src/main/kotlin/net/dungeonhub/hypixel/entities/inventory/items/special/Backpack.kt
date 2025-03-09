package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

class Backpack(raw: NBTCompound) : SkyblockItem(raw) {
    val backpackColor: String //TODO can this be parsed to an actual java color?
        get() = extraAttributes.getString("backpack_color")
}