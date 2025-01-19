package net.dungeonhub.hypixel.client

import net.dungeonhub.hypixel.entities.*
import net.dungeonhub.hypixel.entities.inventory.SkyblockItem
import net.dungeonhub.hypixel.entities.player.HypixelPlayer
import net.dungeonhub.hypixel.entities.player.KnownSocialMediaType
import net.dungeonhub.hypixel.service.FormattingService
import net.dungeonhub.provider.getAsJsonPrimitiveOrNull
import java.util.*

interface ApiClient {
    fun getPlayerData(uuid: UUID): HypixelPlayer?

    fun getHypixelLinkedDiscord(uuid: UUID): String? {
        return getPlayerData(uuid)?.socialMediaLinks?.entries?.firstOrNull { it.key == KnownSocialMediaType.Discord }?.value
    }

    fun getSkyblockProfiles(uuid: UUID): SkyblockProfiles?

    fun getStatsOverview(uuid: UUID): ProfileStatsOverview? {
        val profiles = getSkyblockProfiles(uuid)
            ?: return null

        val selectedProfile = (
                profiles.profiles.firstOrNull { it.selected == true }
                    ?: profiles.profiles.maxByOrNull { it.getCurrentMember(uuid)?.leveling?.experience ?: 0 }
                )
            ?: return null

        val member = selectedProfile.getCurrentMember(uuid) ?: return null

        return getStatsOverview(member, selectedProfile)
    }

    fun getStatsOverview(profileMember: CurrentMember, profile: SkyblockProfile): ProfileStatsOverview? {
        val witherBlades: List<SkyblockItem> =
            profileMember.inventory?.allItems?.flatMap { inventory -> inventory.items }
                ?.mapNotNull { item -> if (item is SkyblockItem) item else null }?.filter { item ->
                    return@filter listOf("HYPERION", "VALKYRIE", "SCYLLA", "ASTRAEA").contains(item.id)
                } ?: emptyList()
        val terminator: List<SkyblockItem> = profileMember.inventory?.allItems?.flatMap { inventory -> inventory.items }
            ?.mapNotNull { item -> if (item is SkyblockItem) item else null }?.filter { item ->
                return@filter item.id == "TERMINATOR"
            } ?: emptyList()
        val goldenDragon: List<Pet> = profileMember.petsData?.pets?.filter { pet ->
            pet.type == "GOLDEN_DRAGON"
        } ?: emptyList()

        val skyblockLevel: Double = profileMember.leveling.experience.toDouble() / 100
        val skillAverage: Double = profileMember.playerData.skillAverage
        val slayerData: Map<KnownSlayerType, Int> = KnownSlayerType.entries.associateWith { slayerType ->
            slayerType.toLevel(profileMember.slayer?.slayerProgress?.get(slayerType)?.xp ?: 0)
        }
        val catacombsLevel: Int = profileMember.dungeons?.catacombsLevel ?: 0

        val purse: String = profileMember.currencies[KnownCurrencyTypes.Coins]?.toLong()
            ?.let { FormattingService.makeNumberReadable(it, 2) } ?: "Empty"
        val bankMoney: String = profile.banking?.getAsJsonPrimitiveOrNull("balance")?.asDouble?.toLong()
            ?.let { FormattingService.makeNumberReadable(it, 2) } ?: "Empty"

        return ProfileStatsOverview(
            profileMember.uuid, profile.cuteName ?: profile.profileId.toString(), buildDescription(
                witherBlades,
                terminator,
                goldenDragon,
                skyblockLevel,
                skillAverage,
                slayerData,
                catacombsLevel,
                purse,
                bankMoney
            )
        )
    }

    fun buildDescription(
        witherBlades: List<SkyblockItem>,
        terminator: List<SkyblockItem>,
        goldenDragon: List<Pet>,
        skyblockLevel: Double,
        skillAverage: Double,
        slayerData: Map<KnownSlayerType, Int>,
        catacombsLevel: Int,
        purse: String,
        bankMoney: String
    ): String {
        val slayers = KnownSlayerType.entries.associateWith { slayerType ->
            when (slayerType) {
                KnownSlayerType.Zombie -> "\uD83E\uDDDF"
                KnownSlayerType.Spider -> "\uD83D\uDD78\uFE0F"
                KnownSlayerType.Wolf -> "\uD83D\uDC3A"
                KnownSlayerType.Enderman -> "\uD83D\uDD2E"
                KnownSlayerType.Blaze -> "\uD83D\uDD25"
                KnownSlayerType.Vampire -> "\uD83E\uDE78"
            }
        }

        return "${
            if (witherBlades.isEmpty()) {
                "\uD83D\uDDE1\uFE0F: No Wither Blade!"
            } else {
                witherBlades.joinToString("\n") { "\uD83D\uDDE1\uFE0F: ${it.rawName}" }
            }
        }\n${
            if (terminator.isEmpty()) {
                "\uD83C\uDFF9: No Terminator!"
            } else {
                //TODO add ultimate enchantment
                terminator.joinToString("\n") { "\uD83C\uDFF9: ${it.rawName}" }
            }
        }\n${
            if (goldenDragon.isEmpty()) {
                "\uD83D\uDC09: No Golden Dragon!"
            } else {
                goldenDragon.joinToString("\n") {
                    val level = it.gdragExpToLevel(it.exp)
                    "\uD83D\uDC09: [Lvl ${level}] Greg (${
                        if (it.heldItem is KnownPetItem) {
                            it.heldItem.displayName
                        } else {
                            it.heldItem?.apiName ?: "None"
                        }
                    })"
                }
            }
        }\n\n<:skyblock_level:1330399754181414994> Skyblock Level: $skyblockLevel\n<:diamond_sword:1330399391839686656> Skill Average: $skillAverage\n\n<:batphone:1330399234813329458> Slayers:${
            slayers.map { (slayerType, emoji) ->
                emoji + " " + (slayerData[slayerType] ?: 0)
            }.joinToString(" ")
            //TODO check private bank
        }\n<:redstone_key:1330398890725478510> Catacombs: $catacombsLevel\n\n<:piggy_bank:1330399968221204560> Purse: $purse\n<:personal_bank:1330399998512468018> Bank: $bankMoney"
    }
}