package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Accessory
import net.dungeonhub.hypixel.entities.inventory.items.id.DyeId
import net.dungeonhub.hypixel.entities.inventory.items.id.KnownDyeId

class BucketOfDye(raw: NBTCompound) : Accessory(raw) {
    val donatedDye: DyeId?
        get() = raw.getString("dye_donated")?.let(KnownDyeId::fromApiName)
}