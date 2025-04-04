package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

class RepellingCandle(raw: NBTCompound) : SkyblockItem(raw) {
    val color: String
        get() = extraAttributes.getString("repelling_color")

    val repellingRadius: Int?
        get() = extraAttributes.getInt("rep_radius", -1).takeIf { it != -1 }

    val repellingParticles: Boolean
        get() = extraAttributes.getInt("rep_particles", 0) == 1
}