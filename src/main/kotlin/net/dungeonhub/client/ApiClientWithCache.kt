package net.dungeonhub.client

import net.dungeonhub.cache.Cache
import net.dungeonhub.entities.SkyblockProfile
import net.dungeonhub.entities.SkyblockProfiles
import net.hypixel.api.reply.PlayerReply
import java.util.UUID

interface ApiClientWithCache : ApiClient {
    val playerDataCache : Cache<PlayerReply.Player, UUID>
    val skyblockProfilesCache : Cache<SkyblockProfiles, UUID>
}