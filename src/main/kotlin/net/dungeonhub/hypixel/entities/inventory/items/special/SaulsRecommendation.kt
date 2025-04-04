package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

class SaulsRecommendation(raw: NBTCompound) : SkyblockItem(raw) {
    val context: String?
        get() = extraAttributes.getString("context")
}