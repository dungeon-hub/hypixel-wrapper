package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

open class BingoCard(raw: NBTCompound) : SkyblockItem(raw) {
    val bingoEvent: Int?
        get() = extraAttributes.getInt("bingo_event", -1).takeIf { it != -1 }

    val playtime: Int?
        get() = extraAttributes.getInt("playtime", -1).takeIf { it != -1 }

    val player: String?
        get() = extraAttributes.getString("player")
}