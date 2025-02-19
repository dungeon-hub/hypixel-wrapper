package net.dungeonhub.hypixel.entities.skyblock

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.dungeonhub.hypixel.entities.skyblock.misc.ProfileGameMode
import net.dungeonhub.provider.getAsJsonObjectOrNull
import net.dungeonhub.provider.getAsJsonPrimitiveOrNull
import net.dungeonhub.provider.getOrNull
import java.util.*

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

    fun getCurrentMember(uuid: UUID): CurrentMember? = currentMembers.firstOrNull { it.uuid == uuid }
}

fun JsonElement.toSkyblockProfile(): SkyblockProfile {
    val profile = SkyblockProfile(
        UUID.fromString(asJsonObject["profile_id"].asString),
        asJsonObject["members"].asJsonObject.loadProfileMembers(),
        asJsonObject.getAsJsonObjectOrNull("banking"),
        asJsonObject.getAsJsonObjectOrNull("community_upgrades"),
        asJsonObject.getOrNull("cute_name")?.asString,
        asJsonObject.getOrNull("selected")?.asBoolean,
        ProfileGameMode.fromApiName(asJsonObject.getAsJsonPrimitiveOrNull("game_mode")?.asString),
        this
    )

    val totalSocialExperience = profile.members.sumOf { it.playerData?.experience?.get(KnownSkill.Social) ?: 0.0 }

    for (member in profile.members) {
        if (member.playerData?.experience?.containsKey(KnownSkill.Social) == true) {
            member.playerData!!.experience?.put(KnownSkill.Social, totalSocialExperience)
        }
    }

    return profile
}