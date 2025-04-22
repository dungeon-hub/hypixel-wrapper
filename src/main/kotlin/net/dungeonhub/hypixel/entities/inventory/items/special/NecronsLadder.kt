package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

class NecronsLadder(raw: NBTCompound) : SkyblockItem(raw) {
    val handlesFound: Int
        get() = extraAttributes.getInt("handles_found", 0)

    val handleLoops: Int
        get() = extraAttributes.getInt("handle_loops", 0)
}