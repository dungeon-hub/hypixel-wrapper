package net.dungeonhub.hypixel.entities.guild

import com.google.gson.JsonObject
import net.dungeonhub.provider.getAsJsonPrimitiveOrNull
import java.time.Instant

class GuildRank(
    val name: String,
    val default: Boolean,
    val tag: String?,
    val created: Instant,
    var priority: Int
)

fun JsonObject.toGuildRank(): GuildRank {
    return GuildRank(
        getAsJsonPrimitive("name").asString,
        getAsJsonPrimitiveOrNull("default")?.asBoolean ?: false,
        getAsJsonPrimitiveOrNull("tag")?.asString,
        Instant.ofEpochMilli(getAsJsonPrimitive("created").asLong),
        getAsJsonPrimitive("priority").asInt
    )
}