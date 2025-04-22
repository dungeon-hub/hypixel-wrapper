package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

class WishingCompass(raw: NBTCompound) : SkyblockItem(raw) {
    val uses: Int
        get() = extraAttributes.getInt("wishing_compass_uses", 1)
}