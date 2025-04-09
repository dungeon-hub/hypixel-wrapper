package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

class DittoSkin(raw: NBTCompound) : SkyblockItem(raw) {
    val skinValue: String?
        get() = extraAttributes.getString("skinValue")

    val skinSignature: String?
        get() = extraAttributes.getString("skinSignature")
}