package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Armor

class EleanorArmor(raw: NBTCompound) : Armor(raw) {
    val coinsGained: Double
        get() = raw.getDouble("coins_gained", 0.0)
}