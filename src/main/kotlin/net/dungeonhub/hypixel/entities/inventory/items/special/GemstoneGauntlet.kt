package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.MiningToolItem
import net.dungeonhub.hypixel.entities.inventory.items.Sword

class GemstoneGauntlet(raw: NBTCompound) : Sword(raw), MiningToolItem {
    val gemstoneGauntletMeter: Int?
        get() = extraAttributes.getInt("gemstone_gauntlet_meter", -1).takeIf { it != -1 }
}