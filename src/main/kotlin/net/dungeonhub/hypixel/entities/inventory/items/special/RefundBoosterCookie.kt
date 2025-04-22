package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem
import java.util.*

class RefundBoosterCookie(raw: NBTCompound) : SkyblockItem(raw) {
    val playerId: UUID?
        get() = extraAttributes.getString("cookie_free_player_id")?.let { UUID.fromString(it) }
}