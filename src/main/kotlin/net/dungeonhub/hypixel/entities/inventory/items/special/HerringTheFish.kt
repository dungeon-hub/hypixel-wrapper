package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

class HerringTheFish(raw: NBTCompound) : SkyblockItem(raw) {
    val notAHint: String?
        get() = extraAttributes.getString("not_a_hint")
}