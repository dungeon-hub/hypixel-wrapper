package net.dungeonhub.hypixel.entities.skyblock

import net.dungeonhub.hypixel.entities.inventory.items.Enchantment
import net.dungeonhub.hypixel.entities.inventory.items.Gear
import net.dungeonhub.hypixel.entities.inventory.items.id.KnownPetItem
import net.dungeonhub.hypixel.entities.inventory.items.special.WitherBlade
import net.dungeonhub.hypixel.entities.skyblock.pet.Pet
import net.dungeonhub.hypixel.entities.skyblock.slayer.KnownSlayerType
import net.dungeonhub.hypixel.service.FormattingService
import java.util.*

class ProfileStatsOverview(
    val uuid: UUID,
    val profileName: String,
    val witherBlades: List<WitherBlade>,
    val terminator: List<Gear>,
    val goldenDragon: List<Pet>,
    val skyblockLevel: Double,
    val skillAverage: Double,
    val slayerData: Map<KnownSlayerType, Int>,
    val catacombsLevel: Int,
    val classAverage: Double,
    val purse: String,
    val bankMoney: String
) {
    val description: String
        get() = "${
            if (witherBlades.isEmpty()) {
                "$witherBladeEmoji: No Wither Blade!"
            } else {
                witherBlades.joinToString("\n") { "$witherBladeEmoji: ${it.rawName}" }
            }
        }\n${
            if (terminator.isEmpty()) {
                "$terminatorEmoji: No Terminator!"
            } else {
                //TODO change once enchants are fully mapped
                terminator.joinToString("\n") {
                    val ultimateEnchant: Pair<Enchantment, Int>? = it.enchantments.entries.firstOrNull { enchant ->
                        enchant.key.apiName.startsWith("ultimate_")
                    }?.toPair()

                    "$terminatorEmoji: ${it.rawName}${
                        if (ultimateEnchant != null) {
                            " (${parseUltimateEnchantment(ultimateEnchant.first.apiName)} ${ultimateEnchant.second})"
                        } else {
                            ""
                        }
                    }"
                }
            }
        }\n${
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
        }\n\n$skyblockLevelEmoji Skyblock Level: $skyblockLevel\n$skillAverageEmoji Skill Average: $skillAverage\n\n$slayerEmoji Slayers:${
            slayerEmojies.map { (slayerType, emoji) ->
                emoji + " " + (slayerData[slayerType] ?: 0)
            }.joinToString(" ")
            //TODO check personal bank
        }\n$catacombsEmoji Catacombs: $catacombsLevel (Class Average ${
            FormattingService.makeDoubleReadable(
                classAverage,
                2
            )
        })\n\n$purseEmoji Purse: $purse\n$bankEmoji Bank: $bankMoney"

    //TODO do properly
    fun parseUltimateEnchantment(apiName: String): String {
        return when (apiName) {
            "ultimate_reiterate" -> "Duplex"
            "ultimate_fatal_tempo" -> "Fatal Tempo"
            "ultimate_soul_eater" -> "Soul Eater"
            "ultimate_rend" -> "Rend"
            "ultimate_swarm" -> "Swarm"
            "ultimate_wise" -> "Ultimate Wise"
            "ultimate_inferno" -> "Inferno"
            else -> apiName
        }
    }

    companion object {
        var witherBladeEmoji = "\uD83D\uDDE1\uFE0F"
        var terminatorEmoji = "\uD83C\uDFF9"
        var goldenDragonEmoji = "\uD83D\uDC09"

        var skyblockLevelEmoji = "<:skyblock_level:1330399754181414994>"
        var skillAverageEmoji = "<:diamond_sword:1330399391839686656>"

        var slayerEmoji = "<:batphone:1330399234813329458>"
        val slayerEmojies = KnownSlayerType.entries.associateWith { slayerType ->
            when (slayerType) {
                KnownSlayerType.Zombie -> "\uD83E\uDDDF"
                KnownSlayerType.Spider -> "\uD83D\uDD78\uFE0F"
                KnownSlayerType.Wolf -> "\uD83D\uDC3A"
                KnownSlayerType.Enderman -> "\uD83D\uDD2E"
                KnownSlayerType.Blaze -> "\uD83D\uDD25"
                KnownSlayerType.Vampire -> "\uD83E\uDE78"
            }
        }.toMutableMap()
        var catacombsEmoji = "<:redstone_key:1330398890725478510>"

        var purseEmoji = "<:piggy_bank:1330399968221204560>"
        var bankEmoji = "<:personal_bank:1330399998512468018>"
    }
}