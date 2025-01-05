package net.dungeonhub.provider

import com.google.gson.*
import com.google.gson.JsonParseException
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import net.dungeonhub.hypixel.entities.*
import net.dungeonhub.hypixel.entities.KnownCurrencyTypes.Companion.toCurrencyType
import java.io.IOException
import java.lang.reflect.Type
import java.time.Instant
import java.util.*
import kotlin.reflect.KClass


object GsonProvider {
    val gson: Gson = GsonBuilder()
        .registerTypeAdapter(Instant::class.java, InstantTypeAdapter())
        .registerTypeAdapter(SkyblockProfileMember::class.java, PolymorphDeserializer<SkyblockProfileMember>())
        .registerTypeAdapter(Skill::class.java, SkillDeserializer())
        .registerTypeAdapter(CurrencyType::class.java, CurrencyDeserializer())
        .registerTypeAdapter(DungeonType::class.java, DungeonTypeDeserializer())
        .registerTypeAdapter(EssenceType::class.java, EssenceTypeDeserializer())
        .setExclusionStrategies(SuperClassExclusionStrategies(SkyblockProfileMember::class.java))
        .create()

    //Thanks https://github.com/iSharipov/gson-adapters
    private class PolymorphDeserializer<T> : JsonDeserializer<T> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, type: Type, context: JsonDeserializationContext): T {
            try {
                val typeClass = Class.forName(type.typeName)
                val jsonType: JsonType = typeClass.getDeclaredAnnotation(JsonType::class.java)
                val property = json.asJsonObject[jsonType.property].asString
                val subtypes: Array<JsonSubtype> = jsonType.subtypes
                val subType = Arrays.stream(subtypes)
                    .filter { subtype: JsonSubtype -> subtype.name == property }.findFirst()
                    .orElseThrow { IllegalArgumentException() }.clazz.java
                return context.deserialize(json, subType)
            } catch (e: Exception) {
                throw JsonParseException("Failed deserialize json", e)
            }
        }
    }

    private class SkillDeserializer : JsonDeserializer<Skill> {
        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Skill {
            return KnownSkill.fromApiName(json!!.asString)
        }
    }

    private class CurrencyDeserializer : JsonDeserializer<CurrencyType> {
        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): CurrencyType {
            return json!!.asString.toCurrencyType()
        }
    }

    private class DungeonTypeDeserializer : JsonDeserializer<DungeonType> {
        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): DungeonType {
            return KnownDungeonType.fromApiName(json!!.asString)
        }
    }

    private class EssenceTypeDeserializer : JsonDeserializer<EssenceType> {
        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): EssenceType {
            return KnownEssenceType.fromApiName(json!!.asString)
        }
    }

    @Target(AnnotationTarget.CLASS)
    @Retention(AnnotationRetention.RUNTIME)
    annotation class JsonType(val property: String, val subtypes: Array<JsonSubtype>)

    annotation class JsonSubtype(val clazz: KClass<*>, val name: String)

    private class SuperClassExclusionStrategies (private val typeToSkip: Class<*>) : ExclusionStrategy {
        override fun shouldSkipClass(clazz: Class<*>): Boolean {
            return false
        }

        override fun shouldSkipField(f: FieldAttributes): Boolean {
            return f.declaringClass == typeToSkip && f.name != "type"
        }
    }

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