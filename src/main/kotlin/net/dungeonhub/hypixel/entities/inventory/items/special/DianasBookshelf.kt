package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

class DianasBookshelf(raw: NBTCompound) : SkyblockItem(raw) {
    val chimeraFound: Int
        get() = extraAttributes.getInt("chimera_found", 0)

    val bookshelfLoops: Int
        get() = extraAttributes.getInt("bookshelf_loops", 0)
}