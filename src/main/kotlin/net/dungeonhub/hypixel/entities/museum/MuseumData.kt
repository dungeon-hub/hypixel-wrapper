package net.dungeonhub.hypixel.entities.museum

import com.google.gson.JsonObject
import net.dungeonhub.mojang.entity.toUUIDUnsafe
import java.util.UUID

class MuseumData(
    val profileId: UUID,
    val museumData: Map<UUID, PlayerMuseumData>
)

fun JsonObject.toMuseumData(profileId: UUID): MuseumData {
    return MuseumData(
        profileId,
        entrySet().associate { (key, value) ->
            key.toUUIDUnsafe() to value.asJsonObject.toPlayerMuseumData()
        }
    )
}