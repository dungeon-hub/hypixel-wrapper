package net.dungeonhub.hypixel.entities.player

interface Rank {
    val apiName: String
    val isSpecial: Boolean
    val isStaff: Boolean
}