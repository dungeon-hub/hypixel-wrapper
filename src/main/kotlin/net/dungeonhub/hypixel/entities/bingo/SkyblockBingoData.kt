package net.dungeonhub.hypixel.entities.bingo

import com.google.gson.JsonObject
import net.dungeonhub.provider.getAsJsonArrayOrNull
import java.util.UUID

class SkyblockBingoData(
    val player: UUID,
    val events: List<BingoEventData>
) {
    val totalPoints
        get() = events.sumOf { it.points }
}

fun JsonObject.toSkyblockBingoData(uuid: UUID): SkyblockBingoData {
    return SkyblockBingoData(
        uuid,
        getAsJsonArrayOrNull("events")?.map { it.asJsonObject.toBingoEventData() } ?: emptyList()
    )
}