package net.dungeonhub.hypixel.client

import com.google.gson.JsonObject
import net.dungeonhub.hypixel.entities.SkyblockProfiles
import net.dungeonhub.hypixel.entities.player.HypixelPlayer
import java.util.*

interface ApiClient {
    fun getPlayerData(uuid: UUID): HypixelPlayer?

    fun getSocialMediaData(uuid: UUID): JsonObject? {
        return getPlayerData(uuid)?.raw?.getAsJsonObject("socialMedia")
    }

    fun getHypixelLinkedDiscord(uuid: UUID): String? {
        return getSocialMediaData(uuid)
            ?.getAsJsonObject("links")
            ?.getAsJsonPrimitive("DISCORD")
            ?.asString
    }

    fun getSkyblockProfiles(uuid: UUID): SkyblockProfiles?
}