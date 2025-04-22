package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

class BlazetekkHamRadio(raw: NBTCompound) : SkyblockItem(raw) {
    val channel: Int?
        get() = extraAttributes.getInt("blazetekk_channel", -1).takeIf { it != -1 }
}