package net.dungeonhub.hypixel.client

import net.dungeonhub.hypixel.entities.guild.Guild
import net.dungeonhub.hypixel.entities.inventory.items.Gear
import net.dungeonhub.hypixel.entities.inventory.items.KnownSkyblockItemId
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem
import net.dungeonhub.hypixel.entities.inventory.items.special.WitherBlade
import net.dungeonhub.hypixel.entities.player.HypixelPlayer
import net.dungeonhub.hypixel.entities.player.KnownSocialMediaType
import net.dungeonhub.hypixel.entities.skyblock.CurrentMember
import net.dungeonhub.hypixel.entities.skyblock.ProfileStatsOverview
import net.dungeonhub.hypixel.entities.skyblock.SkyblockProfile
import net.dungeonhub.hypixel.entities.skyblock.SkyblockProfiles
import net.dungeonhub.hypixel.entities.skyblock.currencies.KnownCurrencyTypes
import net.dungeonhub.hypixel.entities.skyblock.pet.Pet
import net.dungeonhub.hypixel.entities.skyblock.slayer.KnownSlayerType
import net.dungeonhub.hypixel.service.FormattingService
import net.dungeonhub.provider.getAsJsonPrimitiveOrNull
import java.util.*

interface ApiClient {
    fun getPlayerData(uuid: UUID): HypixelPlayer?

    fun getHypixelLinkedDiscord(uuid: UUID): String? {
        return getPlayerData(uuid)?.socialMediaLinks?.entries?.firstOrNull { it.key == KnownSocialMediaType.Discord }?.value
    }

    fun getSkyblockProfiles(uuid: UUID): SkyblockProfiles?

    fun getGuild(name: String): Guild?

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
        val witherBlades: List<WitherBlade> =
            profileMember.inventory?.allItems?.flatMap { inventory -> inventory.items }
                ?.mapNotNull { item -> if (item is SkyblockItem) item else null }?.filterIsInstance<WitherBlade>()
                ?: emptyList()
        val terminator: List<Gear> = profileMember.inventory?.allItems?.flatMap { inventory -> inventory.items }
            ?.mapNotNull { item -> if (item is SkyblockItem) item else null }?.filter { item ->
                return@filter item.id == KnownSkyblockItemId.Terminator
            }?.filterIsInstance<Gear>() ?: emptyList()
        val goldenDragon: List<Pet> = profileMember.petsData?.pets?.filter { pet ->
            pet.type == "GOLDEN_DRAGON"
        } ?: emptyList()

        val skyblockLevel: Double = profileMember.leveling.experience.toDouble() / 100
        val skillAverage: Double = profileMember.playerData.skillAverage
        val slayerData: Map<KnownSlayerType, Int> = KnownSlayerType.entries.associateWith { slayerType ->
            slayerType.toLevel(profileMember.slayer?.slayerProgress?.get(slayerType)?.xp ?: 0)
        }
        val catacombsLevel: Int = profileMember.dungeons?.catacombsLevel ?: 0
        val classAverage: Double = profileMember.dungeons?.classAverage ?: 0.0

        val purse: String = profileMember.currencies[KnownCurrencyTypes.Coins]?.toLong()
            ?.let { FormattingService.makeNumberReadable(it, 2) } ?: "Empty"
        val bankMoney: String = profile.banking?.getAsJsonPrimitiveOrNull("balance")?.asDouble?.toLong()
            ?.let { FormattingService.makeNumberReadable(it, 2) } ?: "Empty"

        return ProfileStatsOverview(
            profileMember.uuid,
            profile.cuteName ?: profile.profileId.toString(),
            witherBlades,
            terminator,
            goldenDragon,
            skyblockLevel,
            skillAverage,
            slayerData,
            catacombsLevel,
            classAverage,
            purse,
            bankMoney
        )
    }
}