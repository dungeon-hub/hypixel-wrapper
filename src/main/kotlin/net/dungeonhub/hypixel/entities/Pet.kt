package net.dungeonhub.hypixel.entities

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import me.nullicorn.nedit.type.NBTCompound
import me.nullicorn.nedit.type.NBTList
import net.dungeonhub.provider.getAsJsonObjectOrNull
import net.dungeonhub.provider.getAsJsonPrimitiveOrNull
import java.util.*

//TODO map type to pet type
class Pet(
    val uuid: UUID?,
    val uniqueId: UUID?,
    val type: String,
    val exp: Double,
    val active: Boolean,
    val tier: SkyblockRarity,
    val heldItem: String?,
    val candyUsed: Int,
    val skin: String?,
    val extraData: JsonObject?
)

fun JsonObject.toPet(): Pet {
    return Pet(
        getAsJsonPrimitiveOrNull("uuid")?.asString?.let { UUID.fromString(it) },
        getAsJsonPrimitiveOrNull("uniqueId")?.asString?.let { UUID.fromString(it) },
        getAsJsonPrimitive("type").asString,
        getAsJsonPrimitive("exp").asDouble,
        getAsJsonPrimitive("active").asBoolean,
        SkyblockRarity.fromApiName(getAsJsonPrimitive("tier").asString),
        getAsJsonPrimitiveOrNull("heldItem")?.asString,
        getAsJsonPrimitive("candyUsed").asInt,
        getAsJsonPrimitiveOrNull("skin")?.asString,
        getAsJsonObjectOrNull("extra")
    )
}

//TODO maybe remove this?
fun NBTCompound.toJsonObject(): JsonObject {
    val result = JsonObject()

    entries.forEach { entry ->
        result.add(entry.key, parseNbtContent(entry.value))
    }

    return result
}

fun NBTList.toJsonArray(): JsonArray {
    val result = JsonArray()

    toList().forEach { result.add(parseNbtContent(it)) }

    return result
}

private fun parseNbtContent(content: Any): JsonElement {
    return when(content) {
        is NBTCompound -> content.toJsonObject()
        is NBTList -> content.toJsonArray()
        is Number -> JsonPrimitive(content)
        else -> JsonPrimitive(content.toString())
    }
}