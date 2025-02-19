package net.dungeonhub.provider

import com.google.gson.*
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import net.dungeonhub.hypixel.entities.player.KnownRank
import net.dungeonhub.hypixel.entities.player.KnownSocialMediaType
import net.dungeonhub.hypixel.entities.player.Rank
import net.dungeonhub.hypixel.entities.player.SocialMediaType
import net.dungeonhub.hypixel.entities.skyblock.KnownSkill
import net.dungeonhub.hypixel.entities.skyblock.Skill
import net.dungeonhub.hypixel.entities.skyblock.SkyblockProfileMember
import net.dungeonhub.hypixel.entities.skyblock.currencies.CurrencyType
import net.dungeonhub.hypixel.entities.skyblock.currencies.EssenceType
import net.dungeonhub.hypixel.entities.skyblock.currencies.KnownCurrencyTypes
import net.dungeonhub.hypixel.entities.skyblock.currencies.KnownCurrencyTypes.Companion.toCurrencyType
import net.dungeonhub.hypixel.entities.skyblock.currencies.KnownEssenceType
import net.dungeonhub.hypixel.entities.skyblock.dungeon.DungeonType
import net.dungeonhub.hypixel.entities.skyblock.dungeon.KnownDungeonType
import net.dungeonhub.hypixel.entities.skyblock.pet.KnownPetItem
import net.dungeonhub.hypixel.entities.skyblock.pet.PetItem
import net.dungeonhub.hypixel.entities.skyblock.slayer.KnownSlayerType
import net.dungeonhub.hypixel.entities.skyblock.slayer.SlayerType
import java.io.IOException
import java.lang.reflect.Type
import java.time.Instant
import java.util.*
import kotlin.reflect.KClass

object GsonProvider {
    val gson: Gson = GsonBuilder()
        .registerTypeAdapter(Instant::class.java, InstantTypeAdapter())
        .registerTypeAdapter(SkyblockProfileMember::class.java, PolymorphDeserializer<SkyblockProfileMember>())
        .registerTypeAdapter(KnownPetItem::class.java, PetItemSerializer())
        .registerTypeAdapter(PetItem::class.java, PetItemSerializer())
        .registerTypeAdapter(KnownSkill::class.java, SkillSerializer())
        .registerTypeAdapter(Skill::class.java, SkillSerializer())
        .registerTypeAdapter(KnownCurrencyTypes::class.java, CurrencySerializer())
        .registerTypeAdapter(CurrencyType::class.java, CurrencySerializer())
        .registerTypeAdapter(KnownDungeonType::class.java, DungeonTypeSerializer())
        .registerTypeAdapter(DungeonType::class.java, DungeonTypeSerializer())
        .registerTypeAdapter(KnownEssenceType::class.java, EssenceTypeSerializer())
        .registerTypeAdapter(EssenceType::class.java, EssenceTypeSerializer())
        .registerTypeAdapter(KnownSlayerType::class.java, SlayerTypeSerializer())
        .registerTypeAdapter(SlayerType::class.java, SlayerTypeSerializer())
        .registerTypeAdapter(KnownSocialMediaType::class.java, SocialMediaTypeSerializer())
        .registerTypeAdapter(SocialMediaType::class.java, SocialMediaTypeSerializer())
        .registerTypeAdapter(KnownRank::class.java, RankSerializer())
        .registerTypeAdapter(Rank::class.java, RankSerializer())
        .enableComplexMapKeySerialization()
        .setExclusionStrategies(SuperClassExclusionStrategies(SkyblockProfileMember::class.java))
        .create()

    // Thanks https://github.com/iSharipov/gson-adapters
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
                    .orElseThrow {
                        IllegalArgumentException("The sub-type wasn't found for the property ($property).")
                    }.clazz.java
                return context.deserialize(json, subType)
                // This exception might not be expected, but it's rather okay to be caught & wrapped and not too generic
            } catch (expected: Exception) {
                throw JsonParseException("Failed to deserialize json", expected)
            }
        }
    }

    private class PetItemSerializer : JsonSerializer<PetItem>, JsonDeserializer<PetItem> {
        override fun serialize(src: PetItem, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement =
            JsonPrimitive(src.apiName)

        override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): PetItem =
            KnownPetItem.fromApiName(json.asString)
    }

    private class RankSerializer : JsonSerializer<Rank>, JsonDeserializer<Rank> {
        override fun serialize(src: Rank, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement =
            JsonPrimitive(src.apiName)

        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Rank =
            KnownRank.fromApiName(json!!.asString)
    }

    private class SocialMediaTypeSerializer : JsonSerializer<SocialMediaType>, JsonDeserializer<SocialMediaType> {
        override fun serialize(
            src: SocialMediaType,
            typeOfSrc: Type?,
            context: JsonSerializationContext?
        ): JsonElement = JsonPrimitive(src.apiName)

        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): SocialMediaType = KnownSocialMediaType.fromApiName(json!!.asString)
    }

    private class SkillSerializer : JsonSerializer<Skill>, JsonDeserializer<Skill> {
        override fun serialize(src: Skill, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement =
            JsonPrimitive(src.apiName)

        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Skill =
            KnownSkill.fromApiName(json!!.asString)
    }

    private class SlayerTypeSerializer : JsonSerializer<SlayerType>, JsonDeserializer<SlayerType> {
        override fun serialize(src: SlayerType, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement =
            JsonPrimitive(src.apiName)

        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): SlayerType =
            KnownSlayerType.fromApiName(json!!.asString)
    }

    private class CurrencySerializer : JsonSerializer<CurrencyType>, JsonDeserializer<CurrencyType> {
        override fun serialize(src: CurrencyType, typeOfSrc: Type, context: JsonSerializationContext): JsonElement =
            JsonPrimitive(src.apiName)

        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): CurrencyType = json!!.asString.toCurrencyType()
    }

    private class DungeonTypeSerializer : JsonSerializer<DungeonType>, JsonDeserializer<DungeonType> {
        override fun serialize(src: DungeonType, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement =
            JsonPrimitive(src.apiName)

        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): DungeonType = KnownDungeonType.fromApiName(json!!.asString)
    }

    private class EssenceTypeSerializer : JsonSerializer<EssenceType>, JsonDeserializer<EssenceType> {
        override fun serialize(src: EssenceType, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement =
            JsonPrimitive(src.apiName)

        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): EssenceType = KnownEssenceType.fromApiName(json!!.asString)
    }

    @Target(AnnotationTarget.CLASS)
    @Retention(AnnotationRetention.RUNTIME)
    annotation class JsonType(val property: String, val subtypes: Array<JsonSubtype>)

    annotation class JsonSubtype(val clazz: KClass<*>, val name: String)

    private class SuperClassExclusionStrategies(private val typeToSkip: Class<*>) : ExclusionStrategy {
        override fun shouldSkipClass(clazz: Class<*>): Boolean = false

        override fun shouldSkipField(f: FieldAttributes): Boolean = f.declaringClass == typeToSkip && f.name != "type"
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
    return if (has(memberName)) {
        getAsJsonObject(memberName)
    } else {
        null
    }
}

fun JsonObject.getAsJsonArrayOrNull(memberName: String): JsonArray? {
    return if (has(memberName)) {
        getAsJsonArray(memberName)
    } else {
        null
    }
}

fun JsonObject.getOrNull(memberName: String): JsonElement? {
    return if (has(memberName)) {
        get(memberName)
    } else {
        null
    }
}

fun JsonObject.getAsJsonPrimitiveOrNull(memberName: String): JsonPrimitive? {
    return if (has(memberName) && get(memberName) !is JsonNull) {
        getAsJsonPrimitive(memberName)
    } else {
        null
    }
}