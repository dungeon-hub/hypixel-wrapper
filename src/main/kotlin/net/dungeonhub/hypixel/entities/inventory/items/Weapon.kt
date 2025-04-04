package net.dungeonhub.hypixel.entities.inventory.items

import me.nullicorn.nedit.type.NBTCompound

open class Weapon(raw: NBTCompound) : Gear(raw), ItemWithHotPotatoBooks, ItemFromBoss, DungeonItem, ItemWithCombatBooks,
    GearFromOphelia {
    val soulEaterData: Double?
        get() = extraAttributes.getDouble("ultimateSoulEaterData", -1.0).takeIf { it != -1.0 }
}