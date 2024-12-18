package net.dungeonhub.hypixel.entities

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.util.UUID

class SkyblockProfile(
    val profileId: UUID,
    val cuteName: String,
    val selected: Boolean,
    val members: JsonObject,
    val banking: JsonObject,
    var raw: JsonElement
)

fun JsonElement.toSkyblockProfile(): SkyblockProfile {
    return SkyblockProfile(
        UUID.fromString(asJsonObject["profile_id"].asString),
        asJsonObject["cute_name"].asString,
        asJsonObject["selected"].asBoolean,
        asJsonObject["members"].asJsonObject,
        asJsonObject["banking"].asJsonObject,
        this
    )
}