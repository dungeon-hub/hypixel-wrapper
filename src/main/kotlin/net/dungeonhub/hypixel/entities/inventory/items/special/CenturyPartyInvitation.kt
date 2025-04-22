package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

class CenturyPartyInvitation(raw: NBTCompound) : SkyblockItem(raw) {
    val levelsFound: List<Int>
        get() = extraAttributes.getList("levels_found")?.mapNotNull { it.toString().toIntOrNull() }
            ?: emptyList()
}