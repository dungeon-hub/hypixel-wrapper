package net.dungeonhub.hypixel.entities.skyblock.stats

import net.dungeonhub.hypixel.entities.inventory.items.Enchantment
import net.dungeonhub.hypixel.entities.inventory.items.KnownEnchantment
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem
import net.dungeonhub.hypixel.entities.inventory.items.id.KnownPetItem
import net.dungeonhub.hypixel.entities.inventory.items.id.WeaponItemId
import net.dungeonhub.hypixel.entities.inventory.items.special.Terminator
import net.dungeonhub.hypixel.entities.inventory.items.special.WitherBlade
import net.dungeonhub.hypixel.entities.skyblock.CurrentMember
import net.dungeonhub.hypixel.entities.skyblock.SkyblockProfile
import net.dungeonhub.hypixel.entities.skyblock.currencies.KnownCurrencyTypes
import net.dungeonhub.hypixel.entities.skyblock.pet.KnownPetType
import net.dungeonhub.hypixel.entities.skyblock.pet.Pet
import net.dungeonhub.hypixel.entities.skyblock.slayer.KnownSlayerType
import net.dungeonhub.hypixel.entities.skyblock.stats.ProfileStatsOverview.Companion.bankEmoji
import net.dungeonhub.hypixel.entities.skyblock.stats.ProfileStatsOverview.Companion.catacombsEmoji
import net.dungeonhub.hypixel.entities.skyblock.stats.ProfileStatsOverview.Companion.goldenDragonEmoji
import net.dungeonhub.hypixel.entities.skyblock.stats.ProfileStatsOverview.Companion.magicalPowerEmoji
import net.dungeonhub.hypixel.entities.skyblock.stats.ProfileStatsOverview.Companion.purseEmoji
import net.dungeonhub.hypixel.entities.skyblock.stats.ProfileStatsOverview.Companion.skillAverageEmoji
import net.dungeonhub.hypixel.entities.skyblock.stats.ProfileStatsOverview.Companion.skyblockLevelEmoji
import net.dungeonhub.hypixel.entities.skyblock.stats.ProfileStatsOverview.Companion.slayerEmoji
import net.dungeonhub.hypixel.entities.skyblock.stats.ProfileStatsOverview.Companion.slayerEmojies
import net.dungeonhub.hypixel.entities.skyblock.stats.ProfileStatsOverview.Companion.terminatorEmoji
import net.dungeonhub.hypixel.entities.skyblock.stats.ProfileStatsOverview.Companion.witherBladeEmoji
import net.dungeonhub.hypixel.service.FormattingService
import net.dungeonhub.provider.getAsJsonPrimitiveOrNull

enum class BuiltInStatsOverviewType(override val value: (profile: SkyblockProfile, profileMember: CurrentMember) -> String) :
    StatsOverviewType {
    Empty({ profile: SkyblockProfile, profileMember: CurrentMember ->
        ""
    }),
    WitherBlades({ profile: SkyblockProfile, profileMember: CurrentMember ->
        val witherBlades: List<WitherBlade> =
            profileMember.inventory?.allItems?.flatMap { inventory -> inventory.items }
                ?.mapNotNull { item -> item as? SkyblockItem }?.filterIsInstance<WitherBlade>()
                ?: emptyList()

        if (witherBlades.isEmpty()) {
            "$witherBladeEmoji: No Wither Blade!"
        } else {
            witherBlades.joinToString("\n") { "$witherBladeEmoji: ${it.rawName}" }
        }
    }),
    Terminators({ profile: SkyblockProfile, profileMember: CurrentMember ->
        val terminator: List<Terminator> =
            profileMember.inventory?.allItems?.flatMap { inventory -> inventory.items }
                ?.mapNotNull { item -> item as? SkyblockItem }?.filter { item ->
                    return@filter item.id == WeaponItemId.Terminator
                }?.filterIsInstance<Terminator>() ?: emptyList()

        if (terminator.isEmpty()) {
            "$terminatorEmoji: No Terminator!"
        } else {
            terminator.joinToString("\n") {
                val ultimateEnchant: Pair<Enchantment, Int>? = it.enchantments.entries.firstOrNull { enchant ->
                    enchant.key.isUltimate()
                }?.toPair()

                "$terminatorEmoji: ${it.rawName}${
                    if (ultimateEnchant != null) {
                        " (${
                            (ultimateEnchant.first as? KnownEnchantment)?.displayName ?: ultimateEnchant.first.apiName
                        } ${ultimateEnchant.second})"
                    } else {
                        ""
                    }
                }"
            }
        }
    }),
    GoldenDragons({ profile: SkyblockProfile, profileMember: CurrentMember ->
        val goldenDragon: List<Pet> = profileMember.petsData?.pets?.filter { pet ->
            pet.type == KnownPetType.GoldenDragon
        } ?: emptyList()

        if (goldenDragon.isEmpty()) {
            "$goldenDragonEmoji: No Golden Dragon!"
        } else {
            goldenDragon.joinToString("\n") {
                val level = it.gdragExpToLevel(it.exp)
                "$goldenDragonEmoji: [Lvl ${level}] Greg ${
                    if (it.heldItem is KnownPetItem) {
                        "(" + it.heldItem.displayName + ")"
                    } else if (it.heldItem != null) {
                        "(" + it.heldItem.apiName + ")"
                    } else {
                        ""
                    }
                }"
            }
        }
    }),
    SkyblockLevel({ profile: SkyblockProfile, profileMember: CurrentMember ->
        val skyblockLevel: Double = profileMember.leveling.experience.toDouble() / 100

        "$skyblockLevelEmoji Skyblock Level: $skyblockLevel"
    }),
    SkillAverage({ profile: SkyblockProfile, profileMember: CurrentMember ->
        val skillAverage: Double = profileMember.playerData.skillAverage

        "$skillAverageEmoji Skill Average: $skillAverage"
    }),
    MagicalPower({ profile: SkyblockProfile, profileMember: CurrentMember ->
        val magicalPower: Int? = profileMember.accessoryBag?.highestMagicalPower

        "$magicalPowerEmoji Magical Power: ${magicalPower ?: "?"}"
    }),
    Slayers({ profile: SkyblockProfile, profileMember: CurrentMember ->
        val slayerData: Map<KnownSlayerType, Int> = KnownSlayerType.entries.associateWith { slayerType ->
            slayerType.toLevel(profileMember.slayer?.slayerProgress?.get(slayerType)?.xp ?: 0)
        }

        "$slayerEmoji Slayers:${
            slayerEmojies.map { (slayerType, emoji) ->
                emoji + " " + (slayerData[slayerType] ?: 0)
            }.joinToString(" ")
        }"
    }),
    Catacombs({ profile: SkyblockProfile, profileMember: CurrentMember ->
        val catacombsLevel: Int = profileMember.dungeons?.catacombsLevel ?: 0
        val classAverage: Double = profileMember.dungeons?.classAverage ?: 0.0

        "$catacombsEmoji Catacombs: $catacombsLevel (Class Average ${
            FormattingService.makeDoubleReadable(
                classAverage,
                2
            )
        })"
    }),
    Purse({ profile: SkyblockProfile, profileMember: CurrentMember ->
        val purse: String = profileMember.currencies[KnownCurrencyTypes.Coins]?.toLong()
            ?.let { FormattingService.makeNumberReadable(it, 2) } ?: "Empty"

        "$purseEmoji Purse: $purse"
    }),
    Bank({ profile: SkyblockProfile, profileMember: CurrentMember ->
        //TODO check personal bank as well
        val bankMoney: String = profile.banking?.getAsJsonPrimitiveOrNull("balance")?.asDouble?.toLong()
            ?.let { FormattingService.makeNumberReadable(it, 2) } ?: "Empty"

        "$bankEmoji Bank: $bankMoney"
    })
}