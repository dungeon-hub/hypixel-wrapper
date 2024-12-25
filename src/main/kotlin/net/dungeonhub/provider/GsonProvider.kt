package net.dungeonhub.provider

import com.google.gson.*
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException
import java.time.Instant

object GsonProvider {
    val gson: Gson = GsonBuilder()
        .registerTypeAdapter(Instant::class.java, InstantTypeAdapter())
        .create()

    private class InstantTypeAdapter : TypeAdapter<Instant>() {
        @Throws(IOException::class)
        override fun write(jsonWriter: JsonWriter, instant: Instant?) {
            if (instant == null) {
                jsonWriter.nullValue()
                return
            }

            jsonWriter.value(instant.toEpochMilli())
        }

        @Throws(IOException::class)
        override fun read(jsonReader: JsonReader): Instant? {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull()
                return null
            }

            return Instant.ofEpochMilli(jsonReader.nextLong())
        }
    }
}

fun JsonObject.getAsJsonObjectOrNull(memberName: String): JsonObject? {
    return if(has(memberName)) {
        getAsJsonObject(memberName)
    } else {
        null
    }
}

fun JsonObject.getAsJsonArrayOrNull(memberName: String): JsonArray? {
    return if(has(memberName)) {
        getAsJsonArray(memberName)
    } else {
        null
    }
}

fun JsonObject.getOrNull(memberName: String): JsonElement? {
    return if(has(memberName)) {
        get(memberName)
    } else {
        null
    }
}

fun JsonObject.getAsJsonPrimitiveOrNull(memberName: String): JsonPrimitive? {
    return if(has(memberName)) {
        getAsJsonPrimitive(memberName)
    } else {
        null
    }
}