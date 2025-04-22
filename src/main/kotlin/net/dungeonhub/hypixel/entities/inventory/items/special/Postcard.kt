package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

class Postcard(raw: NBTCompound) : SkyblockItem(raw) {
    val minionTier: Int
        get() = extraAttributes.getInt("post_card_minion_tier", -1).takeIf { it != -1 }!!

    val minionType: String
        get() = extraAttributes.getString("post_card_minion_type")
}