package net.dungeonhub.hypixel.entities.museum

import com.google.gson.JsonObject
import net.dungeonhub.hypixel.entities.inventory.ItemStack

class PlayerMuseumData(
    val value: Long,
    val appraisal: Boolean,
    val items: Map<String, MuseumItem>,
    val special: List<MuseumItem>
) {
    val allItems: List<ItemStack>
        get() = (items.values + special).flatMap { it.items.items }.filterNotNull()

    val allItemsNotBorrowed: List<ItemStack>
        get() = (items.values + special).filter { it.borrowing == false }.flatMap { it.items.items }.filterNotNull()
}

fun JsonObject.toPlayerMuseumData(): PlayerMuseumData {
    return PlayerMuseumData(
        getAsJsonPrimitive("value").asLong,
        getAsJsonPrimitive("appraisal").asBoolean,
        getAsJsonObject("items").entrySet().associate { (key, value) -> key to value.asJsonObject.toMuseumItem() },
        getAsJsonArray("special").map { it.asJsonObject.toMuseumItem() }
    )
}