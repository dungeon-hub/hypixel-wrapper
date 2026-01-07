package net.dungeonhub.hypixel.client

import net.dungeonhub.hypixel.entities.bingo.SkyblockBingoData
import net.dungeonhub.hypixel.entities.guild.Guild
import net.dungeonhub.hypixel.entities.player.HypixelPlayer
import net.dungeonhub.hypixel.entities.player.KnownSocialMediaType
import net.dungeonhub.hypixel.entities.skyblock.CurrentMember
import net.dungeonhub.hypixel.entities.skyblock.SkyblockProfile
import net.dungeonhub.hypixel.entities.skyblock.SkyblockProfiles
import net.dungeonhub.hypixel.entities.skyblock.statsoverview.ProfileStatsOverview
import net.dungeonhub.hypixel.entities.status.PlayerSession
import java.util.*

interface ApiClient {
    fun getPlayerData(uuid: UUID): HypixelPlayer?

    fun getSession(uuid: UUID): PlayerSession?

    fun getHypixelLinkedDiscord(uuid: UUID): String? {
        return getPlayerData(uuid)?.socialMediaLinks?.entries?.firstOrNull { it.key == KnownSocialMediaType.Discord }?.value
    }

    fun getSkyblockProfiles(uuid: UUID): SkyblockProfiles?

    fun getGuild(name: String): Guild?

    fun getBingoData(uuid: UUID): SkyblockBingoData?

    fun getStatsOverview(uuid: UUID): ProfileStatsOverview? {
        val profiles = getSkyblockProfiles(uuid)
            ?: return null

        val selectedProfile = (
                profiles.profiles.firstOrNull { it.selected == true }
                    ?: profiles.profiles.maxByOrNull { it.getCurrentMember(uuid)?.leveling?.experience ?: 0 }
                )
            ?: return null

        val member = selectedProfile.getCurrentMember(uuid) ?: return null

        return getStatsOverview(selectedProfile, member)
    }

    fun getStatsOverview(profile: SkyblockProfile, profileMember: CurrentMember): ProfileStatsOverview? {
        return ProfileStatsOverview(profile, profileMember)
    }
}