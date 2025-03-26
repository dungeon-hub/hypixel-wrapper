package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

class JournalEntry(raw: NBTCompound) : SkyblockItem(raw) {
    val journalType: String?
        get() = extraAttributes.getString("dungeon_paper_id")
}