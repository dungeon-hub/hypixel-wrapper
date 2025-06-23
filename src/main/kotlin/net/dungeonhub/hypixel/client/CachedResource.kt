package net.dungeonhub.hypixel.client

import net.dungeonhub.cache.CacheType

enum class CachedResource(
    val resourceName: String,
    val cacheTypes: List<CacheType>
) {
    PlayerData("player-data",  listOf(CacheType.Database, CacheType.Disk)),
    SkyblockProfiles("skyblock-profiles",  listOf(CacheType.Database, CacheType.Disk)),
    Guilds("guilds",  listOf(CacheType.Database, CacheType.Disk)),
    BingoData("bingo-data",  listOf(CacheType.Database, CacheType.Disk));
}