package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound

class FungiCutter(raw: NBTCompound) : FarmingTool(raw) {
    val mode: String?
        get() = extraAttributes.getString("fungi_cutter_mode")
}