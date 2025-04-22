package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Accessory

class Abicase(raw: NBTCompound) : Accessory(raw) {
    //TODO maybe map to enum
    val abicaseModel: String?
        get() = extraAttributes.getString("model")
}