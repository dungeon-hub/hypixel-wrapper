package net.dungeonhub.hypixel.entities.inventory

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

open class ItemStack(val raw: NBTCompound) {
    val tag: NBTCompound
        get() = raw.getCompound("tag")

    val name: String
        get() = tag.getString("display.Name")

    val rawName: String
        get() = name.replace(Regex("§[0-9a-fk-or]"), "").trim()

    val extraAttributes: NBTCompound
        get() = tag.getCompound("ExtraAttributes") ?: NBTCompound()
}

fun NBTCompound.toItem(): ItemStack? {
    if (!isValidItem()) return null

    return SkyblockItem.fromNbtCompound(this)
        ?: ItemStack(this)
}

fun NBTCompound.isValidItem(): Boolean {
    return containsKey("tag")
}