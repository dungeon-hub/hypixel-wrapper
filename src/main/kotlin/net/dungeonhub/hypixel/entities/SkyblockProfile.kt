package net.dungeonhub.hypixel.entities

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.dungeonhub.provider.getAsJsonObjectOrNull
import net.dungeonhub.provider.getAsJsonPrimitiveOrNull
import net.dungeonhub.provider.getOrNull
import java.util.UUID

class SkyblockProfile(
    val profileId: UUID,
    val members: List<SkyblockProfileMember>,
    val banking: JsonObject?,
    val communityUpgrades: JsonObject?,
    val cuteName: String?,
    val selected: Boolean?,
    val gameMode: ProfileGameMode,
    var raw: JsonElement
) {
    val currentMembers
        get() = members.filterIsInstance<CurrentMember>()
}

fun JsonElement.toSkyblockProfile(): SkyblockProfile {
    return SkyblockProfile(
        UUID.fromString(asJsonObject["profile_id"].asString),
        asJsonObject["members"].asJsonObject.loadProfileMembers(),
        asJsonObject.getAsJsonObjectOrNull("banking"),
        asJsonObject.getAsJsonObjectOrNull("community_upgrades"),
        asJsonObject.getOrNull("cute_name")?.asString,
        asJsonObject.getOrNull("selected")?.asBoolean,
        ProfileGameMode.fromApiName(asJsonObject.getAsJsonPrimitiveOrNull("game_mode")?.asString),
        this
    )
}