package net.dungeonhub.hypixel.entities.guild

import com.google.gson.JsonObject
import net.dungeonhub.mojang.entity.toUUID
import net.dungeonhub.provider.getAsJsonObjectOrNull
import net.dungeonhub.provider.getAsJsonPrimitiveOrNull
import java.time.Instant
import java.time.LocalDate
import java.util.*

class GuildMember(
    val uuid: UUID,
    val rank: GuildRank?, //TODO check if this could also be not-null
    val joinedAt: Instant?,
    val mutedUntil: Instant?,
    val experienceHistory: Map<LocalDate, Int>,
    val questParticipation: Int?
)

fun JsonObject.toGuildMember(ranks: List<GuildRank>): GuildMember {
    return GuildMember(
        getAsJsonPrimitive("uuid").asString.toUUID(),
        ranks.firstOrNull { it.name == getAsJsonPrimitive("rank").asString },
        getAsJsonPrimitiveOrNull("joinedAt")?.asLong?.let { Instant.ofEpochMilli(it) },
        getAsJsonPrimitiveOrNull("mutedAt")?.asLong?.takeIf { it != 0L }?.let { Instant.ofEpochMilli(it) },
        getAsJsonObjectOrNull("expHistory")?.entrySet()?.associate {
            LocalDate.parse(it.key) to it.value.asInt
        } ?: emptyMap(),
        getAsJsonPrimitiveOrNull("questParticipation")?.asInt
    )
}