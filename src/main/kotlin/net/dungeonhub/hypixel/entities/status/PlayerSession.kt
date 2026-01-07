package net.dungeonhub.hypixel.entities.status

import com.google.gson.JsonObject
import net.dungeonhub.provider.getAsJsonPrimitiveOrNull
import java.util.UUID

class PlayerSession(
    val uuid: UUID,
    val online: Boolean,
    val gameType: String?, // TODO map to enum
    val mode: String?, // TODO map to enum
    val map: String? // TODO map to enum
)

fun JsonObject.toPlayerSession(uuid: UUID): PlayerSession {
    return PlayerSession(
        uuid,
        getAsJsonPrimitive("online").asBoolean,
        getAsJsonPrimitiveOrNull("gameType")?.asString,
        getAsJsonPrimitiveOrNull("mode")?.asString,
        getAsJsonPrimitiveOrNull("map")?.asString
    )
}