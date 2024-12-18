package net.dungeonhub.client

import net.dungeonhub.cache.Cache
import net.dungeonhub.entities.SkyblockProfiles
import net.hypixel.api.reply.PlayerReply
import java.util.*

object DiskCacheApiClient : ApiClientWithCache {
    override val playerDataCache: Cache<PlayerReply.Player, UUID>
        get() = TODO("Not yet implemented")
    override val skyblockProfilesCache: Cache<SkyblockProfiles, UUID>
        get() = TODO("Not yet implemented")

    override fun getPlayerData(uuid: UUID): PlayerReply.Player? {
        TODO("Not yet implemented")
    }

    override fun getSkyblockProfiles(uuid: UUID): SkyblockProfiles? {
        TODO("Not yet implemented")
    }
}