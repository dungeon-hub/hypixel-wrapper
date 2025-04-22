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

    val lore: List<String>
        get() = tag.getList("display.Lore")?.map { it.toString() } ?: emptyList()

    val extraAttributes: NBTCompound
        get() = tag.getCompound("ExtraAttributes") ?: NBTCompound()

    val durabilityLost: Short?
        get() = tag.getShort("Damage", -1).takeIf { it != (-1).toShort() }
}

fun NBTCompound.toItem(): ItemStack? {
    if (!isValidItem()) return null

    return SkyblockItem.fromNbtCompound(this)
        ?: ItemStack(this)
}

fun NBTCompound.isValidItem(): Boolean {
    return containsKey("tag")
}