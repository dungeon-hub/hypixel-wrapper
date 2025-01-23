package net.dungeonhub.hypixel.client

import net.dungeonhub.cache.Cache
import net.dungeonhub.hypixel.entities.skyblock.SkyblockProfiles
import net.dungeonhub.hypixel.entities.player.HypixelPlayer
import java.util.*

object DatabaseCacheApiClient : ApiClientWithCache {
    override val playerDataCache: Cache<HypixelPlayer, UUID>
        get() = TODO("Not yet implemented")
    override val skyblockProfilesCache: Cache<SkyblockProfiles, UUID>
        get() = TODO("Not yet implemented")

    override fun getPlayerData(uuid: UUID): HypixelPlayer? {
        TODO("Not yet implemented")
    }

    override fun getSkyblockProfiles(uuid: UUID): SkyblockProfiles? {
        TODO("Not yet implemented")
    }
}