package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

class RecallPotion(raw: NBTCompound) : SkyblockItem(raw) {
    val biome: String?
        get() = extraAttributes.getString("recall_potion_biome")

    val location: String?
        get() = extraAttributes.getString("recall_potion_location")
}