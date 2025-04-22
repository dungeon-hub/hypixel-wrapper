package net.dungeonhub.mojang.entity

import com.google.gson.JsonObject
import java.util.*

class Player(val id: UUID, val name: String)

fun JsonObject.toPlayer(): Player {
    return Player(
        getAsJsonPrimitive("id").asString.toUUID(),
        getAsJsonPrimitive("name").asString
    )
}

fun String.toUUID(): UUID {
    val regex = "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)".toRegex()

    if (this.matches(regex)) {
        return UUID.fromString(
            replaceFirst(
                regex,
                "$1-$2-$3-$4-$5"
            )
        )
    }

    return UUID.fromString(this)
}