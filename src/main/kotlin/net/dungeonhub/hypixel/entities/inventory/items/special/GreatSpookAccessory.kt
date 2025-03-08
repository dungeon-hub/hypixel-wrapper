package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Accessory

class GreatSpookAccessory(raw: NBTCompound) : Accessory(raw) {
    val year: Int
        get() = extraAttributes.getInt("year", 0)

    val edition: Int?
        get() = extraAttributes.getInt("edition", -1).takeIf { it != -1 }
}