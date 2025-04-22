package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound

class SynthesizerV3(raw: NBTCompound) : SynthesizerV2(raw) {
    val zee: Int?
        get() = extraAttributes.getInt("ZEE", -1).takeIf { it != -1 }
}