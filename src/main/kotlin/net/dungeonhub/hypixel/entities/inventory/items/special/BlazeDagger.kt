package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Sword

class BlazeDagger(raw: NBTCompound) : Sword(raw) {
    val attunement: Int?
        get() = extraAttributes.getInt("td_attune_mode", -1).takeIf { it != -1 }
}