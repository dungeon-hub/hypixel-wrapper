package net.dungeonhub.hypixel.entities.skyblock.statsoverview

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
import net.dungeonhub.hypixel.entities.skyblock.dungeon.KnownDungeonType
import net.dungeonhub.hypixel.entities.skyblock.pet.KnownPetType
import net.dungeonhub.hypixel.entities.skyblock.pet.Pet
import net.dungeonhub.hypixel.entities.skyblock.slayer.KnownSlayerType
import net.dungeonhub.hypixel.entities.skyblock.statsoverview.ProfileStatsOverview.Companion.bankEmoji
import net.dungeonhub.hypixel.entities.skyblock.statsoverview.ProfileStatsOverview.Companion.catacombsEmoji
import net.dungeonhub.hypixel.entities.skyblock.statsoverview.ProfileStatsOverview.Companion.goldenDragonEmoji
import net.dungeonhub.hypixel.entities.skyblock.statsoverview.ProfileStatsOverview.Companion.magicalPowerEmoji
import net.dungeonhub.hypixel.entities.skyblock.statsoverview.ProfileStatsOverview.Companion.notCompletedEmoji
import net.dungeonhub.hypixel.entities.skyblock.statsoverview.ProfileStatsOverview.Companion.purseEmoji
import net.dungeonhub.hypixel.entities.skyblock.statsoverview.ProfileStatsOverview.Companion.skillAverageEmoji
import net.dungeonhub.hypixel.entities.skyblock.statsoverview.ProfileStatsOverview.Companion.skyblockLevelEmoji
import net.dungeonhub.hypixel.entities.skyblock.statsoverview.ProfileStatsOverview.Companion.slayerEmoji
import net.dungeonhub.hypixel.entities.skyblock.statsoverview.ProfileStatsOverview.Companion.slayerEmojies
import net.dungeonhub.hypixel.entities.skyblock.statsoverview.ProfileStatsOverview.Companion.terminatorEmoji
import net.dungeonhub.hypixel.entities.skyblock.statsoverview.ProfileStatsOverview.Companion.witherBladeEmoji
import net.dungeonhub.hypixel.service.FormattingService

enum class BuiltInStatsOverviewType(override val value: (profile: SkyblockProfile, profileMember: CurrentMember) -> String?) :
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

        val highestDungeonFloor = profileMember.dungeons?.dungeonTypes?.get(KnownDungeonType.Catacombs)?.highestTierCompleted ?: -1
        val highestMasterFloor = profileMember.dungeons?.dungeonTypes?.get(KnownDungeonType.MasterCatacombs)?.highestTierCompleted ?: -1

        val highestFloorDisplay = (if (highestDungeonFloor < 7) {
            if (highestDungeonFloor < 0) {
                "Entrance"
            } else {
                "F${highestDungeonFloor + 1}"
            }
        } else if (highestMasterFloor < 7) {
            if (highestMasterFloor < 1) {
                "M1"
            } else {
                "M${highestMasterFloor + 1}"
            }
        } else {
            null
        })?.let {
            " ($notCompletedEmoji $it)"
        } ?: ""

        "$catacombsEmoji Catacombs: $catacombsLevel (Class Average ${
            FormattingService.makeDoubleReadable(
                classAverage,
                2
            )
        })$highestFloorDisplay"
    }),
    Purse({ profile: SkyblockProfile, profileMember: CurrentMember ->
        val purse: String = profileMember.currencies[KnownCurrencyTypes.Coins]?.toLong()
            ?.let { FormattingService.makeNumberReadable(it, 2) } ?: "Empty"

        "$purseEmoji Purse: $purse"
    }),
    Bank({ profile: SkyblockProfile, profileMember: CurrentMember ->
        val coopBankMoney = profile.banking?.balance

        val personalBankMoney = profileMember.profile.bankAccount

        val bankMoney = if (coopBankMoney != null || personalBankMoney != null) {
            FormattingService.makeNumberReadable(((coopBankMoney ?: 0.0) + (personalBankMoney ?: 0.0)).toLong(), 2)
        } else {
            "Empty"
        }

        "$bankEmoji Bank: $bankMoney"
    }),
    MissingSlayerCompletions({ profile: SkyblockProfile, profileMember: CurrentMember ->
        val highestTierKilled = profileMember.slayer?.slayerProgress?.map {
            it.key to (it.value.bossKillsPerTier?.maxOfOrNull { slayerTier -> slayerTier.key } ?: 0)
        }?.associate { it } ?: emptyMap()

        val result = slayerEmojies.mapNotNull { (slayerType, slayerEmoji) ->
            val highestTier = highestTierKilled.getOrDefault(slayerType, 0)

            if(highestTier >= slayerType.maxBossTier) {
                null
            } else {
                "$slayerEmoji T${highestTier + 1}"
            }
        }.joinToString(" | ")

        if(result.isEmpty()) {
            null
        } else {
            "Missing Kills: $result"
        }
    })
}