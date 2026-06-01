package net.dungeonhub.hypixel.entities.skyblock.statsoverview

import net.dungeonhub.hypixel.entities.skyblock.CurrentMember
import net.dungeonhub.hypixel.entities.skyblock.SkyblockProfile
import net.dungeonhub.hypixel.entities.skyblock.slayer.KnownSlayerType
import java.util.*

class ProfileStatsOverview(profile: SkyblockProfile, profileMember: CurrentMember, customStatsOverviewTypes: List<StatsOverviewType>? = null) {
    val uuid: UUID = profileMember.uuid
    val profileName: String = profile.cuteName ?: profile.profileId.toString()

    val description: String = (customStatsOverviewTypes ?: statsOverviewTypes).mapNotNull { type ->
        type.value(profile, profileMember)
    }.joinToString("\n")

    companion object {
        val statsOverviewTypes: MutableList<StatsOverviewType> = mutableListOf(
            BuiltInStatsOverviewType.WitherBlades,
            BuiltInStatsOverviewType.Terminators,
            BuiltInStatsOverviewType.GoldenDragons,
            BuiltInStatsOverviewType.Empty,
            BuiltInStatsOverviewType.SkyblockLevel,
            BuiltInStatsOverviewType.SkillAverage,
            BuiltInStatsOverviewType.MagicalPower,
            BuiltInStatsOverviewType.Empty,
            BuiltInStatsOverviewType.Slayers,
            BuiltInStatsOverviewType.Catacombs,
            BuiltInStatsOverviewType.Empty,
            BuiltInStatsOverviewType.Purse,
            BuiltInStatsOverviewType.Bank
        )

        var witherBladeEmoji = "\uD83D\uDDE1\uFE0F" //🗡️
        var terminatorEmoji = "\uD83C\uDFF9" //🏹
        var goldenDragonEmoji = "\uD83D\uDC09" //🐉

        var skyblockLevelEmoji = "<:skyblock_level:1330399754181414994>"
        var skillAverageEmoji = "<:diamond_sword:1330399391839686656>"
        var magicalPowerEmoji = "\uD83D\uDCAA" //💪

        var slayerEmoji = "<:batphone:1330399234813329458>"
        val slayerEmojies = KnownSlayerType.entries.associateWith { slayerType ->
            when (slayerType) {
                KnownSlayerType.Zombie -> "\uD83E\uDDDF" //🧟
                KnownSlayerType.Spider -> "\uD83D\uDD78\uFE0F" //🕸️
                KnownSlayerType.Wolf -> "\uD83D\uDC3A" //🐺
                KnownSlayerType.Enderman -> "\uD83D\uDD2E" //🔮
                KnownSlayerType.Blaze -> "\uD83D\uDD25" //🔥
                KnownSlayerType.Vampire -> "\uD83E\uDE78" //🩸
            }
        }.toMutableMap()
        var catacombsEmoji = "<:redstone_key:1330398890725478510>"
        var notCompletedEmoji = "\u274C" // ❌

        var purseEmoji = "<:piggy_bank:1330399968221204560>"
        var bankEmoji = "<:personal_bank:1330399998512468018>"
    }
}