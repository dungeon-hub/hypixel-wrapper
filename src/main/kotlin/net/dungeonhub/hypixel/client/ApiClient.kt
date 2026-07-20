package net.dungeonhub.hypixel.client

import net.dungeonhub.hypixel.client.responses.ApiResponse
import net.dungeonhub.hypixel.entities.bingo.SkyblockBingoData
import net.dungeonhub.hypixel.entities.guild.Guild
import net.dungeonhub.hypixel.entities.player.HypixelPlayer
import net.dungeonhub.hypixel.entities.player.KnownSocialMediaType
import net.dungeonhub.hypixel.entities.skyblock.CurrentMember
import net.dungeonhub.hypixel.entities.skyblock.SkyblockProfile
import net.dungeonhub.hypixel.entities.skyblock.SkyblockProfiles
import net.dungeonhub.hypixel.entities.skyblock.statsoverview.ProfileStatsOverview
import net.dungeonhub.hypixel.entities.skyblock.statsoverview.StatsOverviewType
import net.dungeonhub.hypixel.entities.status.PlayerSession
import java.util.*

interface ApiClient {
    fun getPlayerData(uuid: UUID): ApiResponse<HypixelPlayer>

    fun getSession(uuid: UUID): ApiResponse<PlayerSession>

    fun getHypixelLinkedDiscord(uuid: UUID): ApiResponse<String?> {
        return getPlayerData(uuid).map { playerData ->
            return@map playerData.socialMediaLinks.entries.firstOrNull { it.key == KnownSocialMediaType.Discord }?.value
        }
    }

    fun getSkyblockProfiles(uuid: UUID): ApiResponse<SkyblockProfiles>

    fun getGuild(name: String): ApiResponse<Guild>

    fun getPlayerGuild(uuid: UUID): ApiResponse<Guild>

    fun getBingoData(uuid: UUID): ApiResponse<SkyblockBingoData>

    fun getStatsOverview(uuid: UUID, selectedProfile: UUID? = null, statsOverviewTypes: List<StatsOverviewType>? = null): ApiResponse<ProfileStatsOverview?> {
        return getSkyblockProfiles(uuid).map { profiles ->
            val selectedProfile = profiles.profiles.firstOrNull { it.profileId == selectedProfile }
                ?: profiles.profiles.firstOrNull { it.selected == true }
                ?: profiles.profiles.maxByOrNull { it.getCurrentMember(uuid)?.leveling?.experience ?: 0 }
                ?: return@map null

            val member = selectedProfile.getCurrentMember(uuid) ?: return@map null

            return@map getStatsOverview(selectedProfile, member, statsOverviewTypes)
        }
    }

    fun getStatsOverview(profile: SkyblockProfile, profileMember: CurrentMember, statsOverviewTypes: List<StatsOverviewType>? = null): ProfileStatsOverview? {
        return ProfileStatsOverview(profile, profileMember, statsOverviewTypes)
    }
}