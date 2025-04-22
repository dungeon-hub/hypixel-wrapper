package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem
import java.util.*

class TastyMithril(raw: NBTCompound) : SkyblockItem(raw) {
    val gourmandUuid: UUID?
        get() = extraAttributes.getString("gourmand_uuid")?.let { UUID.fromString(it) }
}