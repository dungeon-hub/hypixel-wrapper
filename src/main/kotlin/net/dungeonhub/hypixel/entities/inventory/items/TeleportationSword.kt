package net.dungeonhub.hypixel.entities.inventory.items

import me.nullicorn.nedit.type.NBTCompound

//TODO check fields
open class TeleportationSword(raw: NBTCompound) : Weapon(raw) {
    val etherWarp: Boolean
        get() = extraAttributes.getByte("ethermerge", 0) == 1.toByte()

    val transmissionTuners: Int
        get() = extraAttributes.getInt("tuned_transmission", 0)
}