package net.dungeonhub.hypixel.entities.player

import com.google.gson.JsonObject
import net.dungeonhub.mojang.entity.toUUID
import net.dungeonhub.provider.getAsJsonObjectOrNull
import net.dungeonhub.provider.getAsJsonPrimitiveOrNull
import java.time.Instant
import java.util.*

class HypixelPlayer(
    val internalHypixelId: String,
    val uuid: UUID,
    val playerName: String,
    val displayName: String,
    val firstLogin: Instant?,
    val lastLogin: Instant?,
    val socialMediaLinks: Map<SocialMediaType, String>,
    val rank: Rank,
    val raw: JsonObject
) {
    val discordLink = socialMediaLinks[KnownSocialMediaType.Discord]
}

fun JsonObject.toHypixelPlayer(): HypixelPlayer {
    return HypixelPlayer(
        getAsJsonPrimitive("_id").asString,
        getAsJsonPrimitive("uuid").asString.toUUID(),
        getAsJsonPrimitive("playername").asString,
        getAsJsonPrimitive("displayname").asString,
        getAsJsonPrimitiveOrNull("firstLogin")?.asLong?.let { Instant.ofEpochMilli(it) },
        getAsJsonPrimitiveOrNull("lastLogin")?.asLong?.let { Instant.ofEpochMilli(it) },
        getAsJsonObjectOrNull("socialMedia")?.getAsJsonObjectOrNull("links")?.entrySet()?.associate {
            KnownSocialMediaType.fromApiName(it.key) to it.value.asString
        } ?: emptyMap(),
        KnownRank.fromPlayerObject(this),
        this
    )
}