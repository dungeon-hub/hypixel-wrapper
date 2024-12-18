package net.dungeonhub.client

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.dungeonhub.entities.SkyblockProfile
import net.dungeonhub.entities.SkyblockProfiles
import net.hypixel.api.reply.PlayerReply
import net.hypixel.api.reply.skyblock.SkyBlockProfilesReply
import java.util.*

interface ApiClient {
    fun getPlayerData(uuid: UUID): PlayerReply.Player?

    fun getSocialMediaData(uuid: UUID): JsonObject? {
        return getPlayerData(uuid)?.getObjectProperty("socialMedia")
    }

    fun getHypixelLinkedDiscord(uuid: UUID): String? {
        return getSocialMediaData(uuid)
            ?.getAsJsonObject("links")
            ?.getAsJsonPrimitive("DISCORD")
            ?.asString
    }

    fun getSkyblockProfiles(uuid: UUID): SkyblockProfiles?
}