package net.dungeonhub.hypixel.entities.inventory

import me.nullicorn.nedit.type.NBTCompound

open class ItemStack(val raw: NBTCompound) {
    val tag: NBTCompound
        get() = raw.getCompound("tag")

    val name: String
        get() = tag.getString("display.Name")

    val rawName: String
        get() = name.replace(Regex("§[0-9a-fk-or]"), "").trim()

    val extraAttributes: NBTCompound
        get() = tag.getCompound("ExtraAttributes")
}

fun NBTCompound.toItem(): ItemStack? {
    if (!isValidItem()) return null

    if (isSkyblockItem()) {
        return SkyblockItem(this)
    }

    return ItemStack(this)
}

fun NBTCompound.isValidItem(): Boolean {
    return containsKey("tag")
}