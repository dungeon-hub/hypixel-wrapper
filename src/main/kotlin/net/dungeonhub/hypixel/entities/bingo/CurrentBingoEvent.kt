package net.dungeonhub.hypixel.entities.bingo

import com.google.gson.JsonObject
import java.time.Instant
import java.time.Month
import java.time.Year

class CurrentBingoEvent(
    val id: Int,
    val name: String,
    val start: Instant,
    val end: Instant,
    val type: BingoEventType,
    val goals: List<BingoEventGoal>
) {
    val month: Month
        get() = Month.valueOf(name.split(" ")[0].uppercase())
    val year: Year
        get() = Year.parse(name.split(" ")[1])
}

fun JsonObject.toCurrentBingoEvent(): CurrentBingoEvent {
    return CurrentBingoEvent(
        getAsJsonPrimitive("id").asInt,
        getAsJsonPrimitive("name").asString,
        getAsJsonPrimitive("start").asLong.let(Instant::ofEpochMilli),
        getAsJsonPrimitive("end").asLong.let(Instant::ofEpochMilli),
        getAsJsonPrimitive("modifier").asString.let(KnownBingoEventType::fromApiName),
        getAsJsonArray("goals").map { it.asJsonObject.toBingoEventGoal() }
    )
}