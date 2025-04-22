package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound

open class SynthesizerV2(raw: NBTCompound) : SynthesizerV1(raw) {
    val wai: Int?
        get() = extraAttributes.getInt("WAI", -1).takeIf { it != -1 }
}