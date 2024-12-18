package net.dungeonhub.hypixel.client

import net.dungeonhub.cache.Cache
import net.dungeonhub.hypixel.entities.SkyblockProfiles
import net.hypixel.api.reply.PlayerReply
import java.util.*

interface ApiClientWithCache : ApiClient {
    val playerDataCache : Cache<PlayerReply.Player, UUID>
    val skyblockProfilesCache : Cache<SkyblockProfiles, UUID>
}