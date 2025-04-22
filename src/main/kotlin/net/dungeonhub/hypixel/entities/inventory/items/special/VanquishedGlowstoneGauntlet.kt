package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Gear

class VanquishedGlowstoneGauntlet(raw: NBTCompound) : Gear(raw) {
    val glowstoneBroken: Int
        get() = extraAttributes.getInt("glowing", 0)
}