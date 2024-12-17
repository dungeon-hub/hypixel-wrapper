package net.dungeonhub.client

import com.google.gson.JsonObject
import net.hypixel.api.reply.PlayerReply
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
}