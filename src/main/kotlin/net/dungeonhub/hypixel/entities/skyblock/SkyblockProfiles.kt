package net.dungeonhub.hypixel.entities.skyblock

import java.util.*

data class SkyblockProfiles(
    val owner: UUID,
    val profiles: List<SkyblockProfile>
)