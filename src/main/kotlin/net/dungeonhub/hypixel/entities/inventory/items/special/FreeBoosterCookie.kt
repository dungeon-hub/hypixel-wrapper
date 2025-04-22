package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem
import java.util.*

/**
 * This class is used for both the free Booster Cookie (gained from talking to Elizabeth for the first time) and refunded Booster Cookies (gained when Booster Cookies are refunded by Admins).
 */
class FreeBoosterCookie(raw: NBTCompound) : SkyblockItem(raw) {
    val playerClaimed: UUID?
        get() = extraAttributes.getString("cookie_free_player_id")?.let { UUID.fromString(it) }
}