package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.EditionItem
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem
import net.dungeonhub.hypixel.entities.inventory.items.id.MiscItemId

open class AdminGiftedItem(raw: NBTCompound) : SkyblockItem(raw), EditionItem {
    override val uniqueName: String
        get() = if (id == MiscItemId.AncientElevator && edition != null) {
            "${MiscItemId.AncientElevator.apiName}_EDITIONED"
        } else {
            super<SkyblockItem>.uniqueName
        }
}