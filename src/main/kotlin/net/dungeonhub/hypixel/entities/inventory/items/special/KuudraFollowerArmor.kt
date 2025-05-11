package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Armor
import net.dungeonhub.hypixel.entities.inventory.items.EditionItem

class KuudraFollowerArmor(raw: NBTCompound) : Armor(raw), EditionItem {
    override val uniqueName: String
        get() = super<Armor>.uniqueName
}