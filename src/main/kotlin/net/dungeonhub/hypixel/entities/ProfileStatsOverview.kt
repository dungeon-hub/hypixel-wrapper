package net.dungeonhub.hypixel.entities

import java.util.UUID

//TODO move certain stats into here and make full generation in a method in this class
class ProfileStatsOverview(
    val uuid: UUID,
    val profileName: String,
    val description: String
)