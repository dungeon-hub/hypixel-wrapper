package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Accessory

class BucketOfDye(raw: NBTCompound) : Accessory(raw) {
    val donatedDye: String? //TODO map to enum?
        get() = raw.getString("dye_donated")
}