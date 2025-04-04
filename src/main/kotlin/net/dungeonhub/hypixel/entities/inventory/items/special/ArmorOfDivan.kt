package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Armor
import net.dungeonhub.hypixel.entities.inventory.items.PowderCoatableItem

class ArmorOfDivan(raw: NBTCompound) : Armor(raw), PowderCoatableItem {
    val gemstoneSlots: Int
        get() = extraAttributes.getInt("gemstone_slots", 0)
}