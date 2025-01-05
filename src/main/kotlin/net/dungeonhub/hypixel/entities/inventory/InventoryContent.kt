package net.dungeonhub.hypixel.entities.inventory

import com.google.gson.JsonObject
import me.nullicorn.nedit.NBTReader
import me.nullicorn.nedit.type.NBTCompound

class InventoryContent(
    val type: Int,
    val items: List<InventoryItemStack?>
)

fun JsonObject.toInventoryContentData(): InventoryContent {
    return InventoryContent(
        getAsJsonPrimitive("type").asInt,
        NBTReader.readBase64(getAsJsonPrimitive("data").asString).getList("i").map { (it as NBTCompound).toInventoryItem() }
    )
}