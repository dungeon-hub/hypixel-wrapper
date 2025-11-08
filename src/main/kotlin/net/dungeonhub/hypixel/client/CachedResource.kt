package net.dungeonhub.hypixel.client

enum class CachedResource(
    val resourceName: String
) {
    PlayerData("player-data"),
    SkyblockProfiles("skyblock-profiles"),
    Guilds("guilds"),
    BingoData("bingo-data");
}