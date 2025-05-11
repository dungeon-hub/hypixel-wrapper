package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.EditionItem
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem
import net.dungeonhub.hypixel.entities.inventory.items.id.MiscItemId

class CreativeMind(raw: NBTCompound) : SkyblockItem(raw), EditionItem {
    override val uniqueName: String
        get() = if (edition == null) {
            "${MiscItemId.CreativeMind.apiName}_UNEDITIONED"
        } else {
            super<SkyblockItem>.uniqueName
        }
}