package net.dungeonhub.hypixel.entities.guild

import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import net.dungeonhub.provider.getAsJsonArrayOrNull
import net.dungeonhub.provider.getAsJsonObjectOrNull
import net.dungeonhub.provider.getAsJsonPrimitiveOrNull
import net.hypixel.api.data.type.GameType
import java.time.Instant
import java.time.ZoneId

class Guild(
    val id: String,
    val name: String,
    val displayName: String,
    val description: String?,
    val tag: String?,
    val tagColor: String?,
    val creationDate: Instant,
    val experience: Long,
    val isPubliclyListed: Boolean,
    val isJoinable: Boolean,
    val members: List<GuildMember>,
    val ranks: List<GuildRank>,
    val preferredGames: List<GameType>,
    val guildExp: Map<GameType, Int>,
    val achievements: Map<String, Int>,
    val coins: Int,
    val coinsEver: Int
) {
    val customRanks
        get() = ranks.filterIsInstance<CustomGuildRank>()
}

fun JsonObject.toGuild(): Guild {
    val ranks = (getAsJsonArrayOrNull("ranks")?.map { it.asJsonObject.toGuildRank() }?.sortedBy { it.priority }
        ?: emptyList()).toMutableList()

    val creationDate =
        Instant.ofEpochMilli(getAsJsonPrimitive("created").asLong).atZone(ZoneId.of("America/New_York")).toInstant()

    if (!ranks.any { it.name == GuildMasterRank.GUILD_MASTER_NAME }) {
        ranks.add(
            GuildMasterRank(
                "GM",
                false,
                creationDate,
                50
            )
        )
    }

    ranks.withIndex().forEach { (index, rank) ->
        rank.priority = index + 1
    }

    return Guild(
        id = getAsJsonPrimitive("_id").asString,
        name = getAsJsonPrimitive("name_lower").asString,
        displayName = getAsJsonPrimitive("name").asString,
        description = getAsJsonPrimitiveOrNull("description")?.asString,
        tag = getAsJsonPrimitiveOrNull("tag")?.asString,
        tagColor = getAsJsonPrimitiveOrNull("tagColor")?.asString,
        creationDate = creationDate,
        experience = getAsJsonPrimitive("exp").asLong,
        isPubliclyListed = getAsJsonPrimitiveOrNull("publiclyListed")?.asBoolean == true,
        isJoinable = getAsJsonPrimitiveOrNull("joinable")?.asBoolean == true,
        members = getAsJsonArrayOrNull("members")?.map { it.asJsonObject.toGuildMember(ranks) } ?: emptyList(),
        ranks = ranks,
        preferredGames = getAsJsonArrayOrNull("preferredGames")?.mapNotNull {
            if (it is JsonPrimitive && it.isNumber) {
                GameType.fromId(it.asInt)
            } else {
                try {
                    GameType.fromId(it.asString.toInt())
                } catch (_: NumberFormatException) {
                    try {
                        GameType.valueOf(it.asString)
                    } catch (_: IllegalArgumentException) {
                        null
                    }
                }
            }
        } ?: emptyList(),
        guildExp = getAsJsonObjectOrNull("guildExpByGameType")?.entrySet()?.mapNotNull {
            try {
                GameType.fromId(it.key.toInt())
            } catch (_: NumberFormatException) {
                try {
                    GameType.valueOf(it.key)
                } catch (_: IllegalArgumentException) {
                    return@mapNotNull null
                }
            } to it.value.asInt
        }?.toMap() ?: emptyMap(),
        achievements = getAsJsonObjectOrNull("achievements")?.entrySet()?.associate {
            it.key to it.value.asInt
        } ?: emptyMap(),
        coins = getAsJsonPrimitiveOrNull("coins")?.asInt ?: 0,
        coinsEver = getAsJsonPrimitiveOrNull("coinsEver")?.asInt ?: 0
    )
}