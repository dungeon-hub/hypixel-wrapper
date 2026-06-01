package net.dungeonhub.hypixel.client

enum class CachedResource(
    val resourceName: String
) {
    PlayerData("player-data"),
    PlayerSession("player-session"),
    SkyblockProfiles("skyblock-profiles"),
    Guilds("guilds"),
    PlayerGuilds("player-guilds"),
    BingoData("bingo-data");
}