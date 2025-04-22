package net.dungeonhub.hypixel.entities.inventory.items

import me.nullicorn.nedit.type.NBTCompound

open class TeleportationSword(raw: NBTCompound) : Sword(raw) {
    val etherWarp: Boolean
        get() = extraAttributes.getByte("ethermerge", 0) == 1.toByte()

    val transmissionTuners: Int
        get() = extraAttributes.getInt("tuned_transmission", 0)
}