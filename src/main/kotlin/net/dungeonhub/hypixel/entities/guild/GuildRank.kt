package net.dungeonhub.hypixel.entities.guild

import com.google.gson.JsonObject
import net.dungeonhub.provider.getAsJsonPrimitiveOrNull
import java.time.Instant

interface GuildRank {
    val name: String
    val tag: String?
    val default: Boolean
    val owner: Boolean
    val created: Instant
    var priority: Int
}

fun JsonObject.toGuildRank(): GuildRank {
    val name = getAsJsonPrimitive("name").asString

    if (name == GuildMasterRank.GUILD_MASTER_NAME) {
        return GuildMasterRank(
            getAsJsonPrimitiveOrNull("tag")?.asString,
            getAsJsonPrimitiveOrNull("default")?.asBoolean ?: false,
            Instant.ofEpochMilli(getAsJsonPrimitive("created").asLong),
            getAsJsonPrimitive("priority").asInt
        )
    }

    return CustomGuildRank(
        name,
        getAsJsonPrimitiveOrNull("tag")?.asString,
        getAsJsonPrimitiveOrNull("default")?.asBoolean ?: false,
        Instant.ofEpochMilli(getAsJsonPrimitive("created").asLong),
        getAsJsonPrimitive("priority").asInt
    )
}