package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

class TuningFork(raw: NBTCompound) : SkyblockItem(raw) {
    val tuning: Int?
        get() = extraAttributes.getInt("tuning_fork_tuning", -1).takeIf { it != -1 }
}