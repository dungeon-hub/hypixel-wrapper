package net.dungeonhub.hypixel.client

import net.dungeonhub.cache.memory.HashMapCache
import net.dungeonhub.hypixel.entities.bingo.SkyblockBingoData
import net.dungeonhub.hypixel.entities.guild.Guild
import net.dungeonhub.hypixel.entities.player.HypixelPlayer
import net.dungeonhub.hypixel.entities.skyblock.SkyblockProfiles
import java.util.*

object MemoryCacheApiClient : ApiClientWithCache {
    override val playerDataCache = HashMapCache<HypixelPlayer, UUID> { it.uuid }
    override val skyblockProfilesCache = HashMapCache<SkyblockProfiles, UUID> { it.owner }
    override val guildCache = HashMapCache<Guild, String> { it.name }
    override val bingoDataCache = HashMapCache<SkyblockBingoData, UUID> { it.player }
}