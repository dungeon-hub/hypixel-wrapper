package net.dungeonhub.hypixel.entities.inventory.items

import me.nullicorn.nedit.type.NBTCompound

open class Accessory(raw: NBTCompound) : SkyblockItem(raw), ReforgeableItem {
    val enrichment: String?
        get() = extraAttributes.getString("talisman_enrichment")
}