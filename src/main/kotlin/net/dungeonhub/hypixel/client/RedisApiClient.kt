package net.dungeonhub.hypixel.client

import net.dungeonhub.cache.Cache
import net.dungeonhub.hypixel.entities.bingo.SkyblockBingoData
import net.dungeonhub.hypixel.entities.guild.Guild
import net.dungeonhub.hypixel.entities.player.HypixelPlayer
import net.dungeonhub.hypixel.entities.skyblock.SkyblockProfiles
import java.util.UUID

object RedisApiClient : ApiClientWithCache {
    override val playerDataCache: Cache<HypixelPlayer, UUID>
        get() = TODO("Not yet implemented")
    override val skyblockProfilesCache: Cache<SkyblockProfiles, UUID>
        get() = TODO("Not yet implemented")
    override val guildCache: Cache<Guild, String>
        get() = TODO("Not yet implemented")
    override val bingoDataCache: Cache<SkyblockBingoData, UUID>
        get() = TODO("Not yet implemented")
}