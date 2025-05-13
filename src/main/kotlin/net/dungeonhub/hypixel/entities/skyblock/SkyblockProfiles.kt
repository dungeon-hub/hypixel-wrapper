package net.dungeonhub.hypixel.entities.skyblock

import net.dungeonhub.hypixel.entities.inventory.SkyblockRarity
import net.dungeonhub.hypixel.entities.skyblock.misc.ProfileGameMode
import net.dungeonhub.hypixel.entities.skyblock.pet.KnownPetType
import java.util.*

class SkyblockProfiles(
    val owner: UUID,
    val profiles: List<SkyblockProfile>
) {
    val bingoRank: SkyblockRarity?
        get() = profiles.filter { it.gameMode == ProfileGameMode.Bingo }.map { it.getCurrentMember(owner) }
            .flatMap { it?.petsData?.pets ?: emptyList() }.firstOrNull { it.type == KnownPetType.Bingo }?.tier
}