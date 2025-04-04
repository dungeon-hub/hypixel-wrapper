package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem
import java.util.*

class FreeBoosterCookie(raw: NBTCompound) : SkyblockItem(raw) {
    val playerClaimed: UUID?
        get() = extraAttributes.getString("cookie_free_player_id")?.let { UUID.fromString(it) }
}