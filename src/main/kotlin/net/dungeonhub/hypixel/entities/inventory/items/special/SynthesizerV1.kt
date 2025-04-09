package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Gear

open class SynthesizerV1(raw: NBTCompound) : Gear(raw) {
    val exe: Int?
        get() = extraAttributes.getInt("EXE", 0)
}