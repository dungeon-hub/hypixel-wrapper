package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Armor

class FinalDestinationArmor(raw: NBTCompound) : Armor(raw) {
    val endermanKills: Int
        get() = extraAttributes.getInt("eman_kills", 0)
}