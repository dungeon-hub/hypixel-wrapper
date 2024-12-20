package net.dungeonhub.mojang.entity

import com.google.gson.JsonObject
import java.util.UUID

class Player(val id: UUID, val name: String)

fun JsonObject.toPlayer(): Player {
    return Player(
        getAsJsonPrimitive("id").asString.toUUIDUnsafe(),
        getAsJsonPrimitive("name").asString
    )
}

//TODO make safe -> check for format
fun String.toUUIDUnsafe(): UUID {
    return UUID.fromString(
        replaceFirst(
            "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)".toRegex(),
            "$1-$2-$3-$4-$5"
        )
    )
}