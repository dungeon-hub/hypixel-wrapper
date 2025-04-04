package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

class FishFood(raw: NBTCompound) : SkyblockItem(raw) {
    val petExperience: Int
        get() = extraAttributes.getInt("pet_exp", 0)
}