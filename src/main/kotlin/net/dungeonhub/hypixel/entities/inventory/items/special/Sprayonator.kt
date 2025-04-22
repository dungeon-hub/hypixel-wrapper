package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem
import net.dungeonhub.hypixel.entities.inventory.items.id.KnownSkyblockItemId
import net.dungeonhub.hypixel.entities.inventory.items.id.SkyblockItemId

class Sprayonator(raw: NBTCompound) : SkyblockItem(raw) {
    val sprayItem: SkyblockItemId?
        get() = extraAttributes.getString("spray_item")?.let { KnownSkyblockItemId.fromApiName(it) }
}