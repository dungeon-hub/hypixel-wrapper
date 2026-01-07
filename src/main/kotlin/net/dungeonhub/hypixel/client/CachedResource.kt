package net.dungeonhub.hypixel.client

enum class CachedResource(
    val resourceName: String
) {
    PlayerData("player-data"),
    PlayerSession("player-session"),
    SkyblockProfiles("skyblock-profiles"),
    Guilds("guilds"),
    BingoData("bingo-data");
}