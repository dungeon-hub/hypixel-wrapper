package net.dungeonhub.hypixel.entities.skyblock

import net.dungeonhub.hypixel.entities.skyblock.misc.MemberPlayerData
import net.dungeonhub.hypixel.entities.skyblock.pet.MemberPetsData
import net.dungeonhub.hypixel.entities.skyblock.slayer.MemberSlayerData
import net.dungeonhub.hypixel.entities.skyblock.stats.MemberPlayerStats
import java.util.*

class PastMember(
    override val uuid: UUID,
    override val profile: MemberProfileData,
    override val leveling: MemberLeveling,
    override val playerData: MemberPlayerData,
    override val playerStats: MemberPlayerStats?,
    override val slayer: MemberSlayerData?,
    val petsData: MemberPetsData?
) : SkyblockProfileMember(uuid, "past", profile, leveling, playerData, playerStats, slayer)