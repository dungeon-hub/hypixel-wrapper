package net.dungeonhub.hypixel.entities.inventory

import com.google.gson.JsonObject
import me.nullicorn.nedit.NBTReader
import me.nullicorn.nedit.type.NBTCompound

class InventoryContent(
    val type: Int,
    val data: String
) {
    val items: List<ItemStack?>
        get() = NBTReader.readBase64(data).getList("i").map { (it as NBTCompound).toItem() }
}

fun JsonObject.toInventoryContentData(): InventoryContent {
    return InventoryContent(
        getAsJsonPrimitive("type").asInt,
        getAsJsonPrimitive("data").asString
    )
}