package net.dungeonhub.hypixel.entities.skyblock.statsoverview

import net.dungeonhub.hypixel.entities.skyblock.CurrentMember
import net.dungeonhub.hypixel.entities.skyblock.SkyblockProfile

interface StatsOverviewType {
    val value: (profile: SkyblockProfile, profileMember: CurrentMember) -> String
}