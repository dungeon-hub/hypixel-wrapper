package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Armor
import net.dungeonhub.hypixel.entities.inventory.items.EditionItem
import net.dungeonhub.hypixel.entities.inventory.items.id.ArmorItemId

class SpaceHelmet(raw: NBTCompound) : Armor(raw), EditionItem {
    override val uniqueName: String
        get() = if (edition != null) {
            "${ArmorItemId.SpaceHelmet.apiName}_EDITIONED"
        } else {
            super<Armor>.uniqueName
        }
}