package net.dungeonhub.hypixel.entities.inventory

import com.google.gson.JsonObject
import me.nullicorn.nedit.NBTReader
import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.BackpackContent
import java.io.ByteArrayInputStream

class InventoryContent(
    val type: Int,
    val data: String
) {
    val items: List<ItemStack?>
        get() = NBTReader.readBase64(data).getItems()
}

fun JsonObject.toInventoryContentData(): InventoryContent {
    return InventoryContent(
        getAsJsonPrimitive("type").asInt,
        getAsJsonPrimitive("data").asString
    )
}

fun ByteArray.readItemStacks(): List<ItemStack?> {
    return NBTReader.read(ByteArrayInputStream(this)).getItems()
}

fun ByteArray.toBackpackContentData(): BackpackContent {
    return BackpackContent(
        this
    )
}

fun NBTCompound.getItems(): List<ItemStack?> {
    return getList("i").map { (it as NBTCompound).toItem() }
}