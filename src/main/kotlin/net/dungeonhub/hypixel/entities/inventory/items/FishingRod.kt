package net.dungeonhub.hypixel.entities.inventory.items

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.AppliedRodPart.Companion.toRodPart

open class FishingRod(raw: NBTCompound) : Gear(raw), FishingTool, ItemWithHotPotatoBooks {
    val line: AppliedRodPart?
        get() = extraAttributes.getCompound("line")?.toRodPart()

    val hook: AppliedRodPart?
        get() = extraAttributes.getCompound("hook")?.toRodPart()

    val sinker: AppliedRodPart?
        get() = extraAttributes.getCompound("sinker")?.toRodPart()
}