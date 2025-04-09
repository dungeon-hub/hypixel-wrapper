package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.ShensAuctionItem
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

class PuzzleCube(raw: NBTCompound) : SkyblockItem(raw), ShensAuctionItem {
    val puzzlesSolved: Int
        get() = extraAttributes.getInt("puzzles_solved", 0)
}