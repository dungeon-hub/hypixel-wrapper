package net.dungeonhub.hypixel.entities.skyblock

import com.google.gson.JsonObject
import net.dungeonhub.hypixel.entities.skyblock.misc.MemberPlayerData
import net.dungeonhub.hypixel.entities.skyblock.pet.MemberPetsData
import net.dungeonhub.hypixel.entities.skyblock.slayer.MemberSlayerData
import net.dungeonhub.hypixel.entities.skyblock.stats.MemberPlayerStats
import java.util.*

data class PastMember(
    override val uuid: UUID,
    override val profile: JsonObject,
    override val leveling: MemberLeveling,
    override val playerData: MemberPlayerData,
    override val playerStats: MemberPlayerStats?,
    override val slayer: MemberSlayerData?,
    val petsData: MemberPetsData?,
    override val raw: JsonObject
) : SkyblockProfileMember(uuid, profile, leveling, playerData, playerStats, slayer, raw) {
    override val type = "past"
}