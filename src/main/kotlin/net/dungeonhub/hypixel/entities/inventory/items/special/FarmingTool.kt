package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Gear
import net.dungeonhub.hypixel.entities.inventory.items.ItemWithCombatBooks
import net.dungeonhub.hypixel.entities.inventory.items.ItemWithHotPotatoBooks

/**
 * This class is for all Hoes and Axes
 */
open class FarmingTool(raw: NBTCompound) : Gear(raw), ItemWithHotPotatoBooks, ItemWithCombatBooks {
    val farmedCultivating: Int?
        get() = extraAttributes.getInt("farmed_cultivating", -1).takeIf { it != -1 }

    val farmedCrops: Int?
        get() = extraAttributes.getInt("mined_crops", -1).takeIf { it != -1 }

    val farmingForDummies: Int
        get() = extraAttributes.getInt("farming_for_dummies_count", 0)
}