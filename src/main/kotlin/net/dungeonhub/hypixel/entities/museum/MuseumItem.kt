package net.dungeonhub.hypixel.entities.museum

import com.google.gson.JsonObject
import net.dungeonhub.hypixel.entities.inventory.InventoryContent
import net.dungeonhub.hypixel.entities.inventory.toInventoryContentData
import net.dungeonhub.provider.getAsJsonPrimitiveOrNull
import java.time.Instant

class MuseumItem(
    val items: InventoryContent,
    val featuresSlot: String?,
    val borrowing: Boolean?,
    val donatedTime: Instant
)

fun JsonObject.toMuseumItem(): MuseumItem {
    return MuseumItem(
        getAsJsonObject("items").toInventoryContentData(),
        getAsJsonPrimitiveOrNull("featured_slot")?.asString,
        getAsJsonPrimitiveOrNull("borrowing")?.asBoolean,
        Instant.ofEpochMilli(getAsJsonPrimitive("donated_time").asLong)
    )
}