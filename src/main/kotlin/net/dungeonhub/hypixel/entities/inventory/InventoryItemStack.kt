package net.dungeonhub.hypixel.entities.inventory

import me.nullicorn.nedit.type.NBTCompound

class InventoryItemStack(
    val tag: NBTCompound,
    val raw: NBTCompound
) {
    val name: String
        get() = tag.getString("display.Name")

    val rawName: String
        get() = name.replace(Regex("§[0-9a-fk-or]"), "").trim()
}

fun NBTCompound.toInventoryItem(): InventoryItemStack? {
    if(!isValidItem()) return null

    return InventoryItemStack(
        getCompound("tag"),
        this
    )
}

fun NBTCompound.isValidItem(): Boolean {
    return containsKey("tag")
}