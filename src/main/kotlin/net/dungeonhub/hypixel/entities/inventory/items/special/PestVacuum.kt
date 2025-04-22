package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Gear

class PestVacuum(raw: NBTCompound) : Gear(raw) {
    val bookwormBooks: Int
        get() = extraAttributes.getInt("bookworm_books", 0)
}