package net.dungeonhub.cache.database

import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Sorts
import net.dungeonhub.cache.Cache
import net.dungeonhub.cache.memory.CacheElement
import net.dungeonhub.provider.GsonProvider
import org.bson.Document
import java.time.Instant
import java.util.Date
import java.util.stream.Stream

class MongoCache<T, K>(
    collection: MongoCollection<Document>,
    private val typeToken: TypeToken<CacheElement<T>>,
    private val keyFunction: (T) -> K,
    private val keySerializer: (K) -> String = { it.toString() }
) : Cache<T, K> {

    private val collection: MongoCollection<Document> = collection.withDocumentClass(Document::class.java)

    override fun retrieveElement(key: K): CacheElement<T>? {
        val keyString = keySerializer(key)
        val document = collection
            .find(Filters.eq(KEY_FIELD, keyString))
            .sort(Sorts.descending(TIMESTAMP_FIELD))
            .first() ?: return null
        return deserialize(document)
    }

    override fun retrieveAllElements(): Stream<CacheElement<T>> {
        val latestByKey = linkedMapOf<String, CacheElement<T>>()
        collection.find().iterator().use { cursor ->
            while (cursor.hasNext()) {
                val document = cursor.next()
                val key = document.getString(KEY_FIELD) ?: document[KEY_FIELD]?.toString() ?: continue
                val element = deserialize(document) ?: continue
                val current = latestByKey[key]
                if (current == null || current.timeAdded.isBefore(element.timeAdded)) {
                    latestByKey[key] = element
                }
            }
        }
        return latestByKey.values.stream()
    }

    override fun store(value: T) {
        val key = keyFunction(value)
        val serializedKey = keySerializer(key)
        val timestamp = Instant.now()
        val valueElement = GsonProvider.gson.toJsonTree(value)
        val document = Document()
        document[KEY_FIELD] = serializedKey
        document[TIMESTAMP_FIELD] = timestamp
        document[VALUE_FIELD] = jsonElementToBsonValue(valueElement)
        collection.insertOne(document)
    }

    override fun invalidateEntry(key: K) {
        val serializedKey = keySerializer(key)
        collection.deleteMany(Filters.eq(KEY_FIELD, serializedKey))
    }

    private fun deserialize(document: Document): CacheElement<T>? {
        val timestamp = extractInstant(document[TIMESTAMP_FIELD]) ?: return null
        val rawValue = if (document.containsKey(VALUE_FIELD)) document[VALUE_FIELD] else return null
        return try {
            val valueJson = bsonValueToJsonElement(rawValue)
            val elementJson = JsonObject().apply {
                addProperty("timeAdded", timestamp.toEpochMilli())
                add("value", valueJson)
            }
            GsonProvider.gson.fromJson<CacheElement<T>>(elementJson, typeToken.type)
        } catch (_: JsonSyntaxException) {
            null
        } catch (_: ClassCastException) {
            null
        }
    }

    companion object {
        private const val KEY_FIELD = "key"
        private const val TIMESTAMP_FIELD = "timestamp"
        private const val VALUE_FIELD = "value"
    }

    private fun jsonElementToBsonValue(element: JsonElement): Any? = when {
        element.isJsonNull -> null
        element.isJsonPrimitive -> primitiveToValue(element.asJsonPrimitive)
        element.isJsonArray -> element.asJsonArray.map { jsonElementToBsonValue(it) }
        element.isJsonObject -> element.asJsonObject.entrySet()
            .fold(Document()) { acc, (key, value) ->
                acc.apply { this[key] = jsonElementToBsonValue(value) }
            }
        else -> null
    }

    private fun primitiveToValue(primitive: JsonPrimitive): Any? = when {
        primitive.isBoolean -> primitive.asBoolean
        primitive.isNumber -> primitive.asString.toLongOrNull()
            ?: primitive.asString.toDoubleOrNull()
            ?: primitive.asBigDecimal
        primitive.isString -> primitive.asString
        else -> primitive.asString
    }

    private fun bsonValueToJsonElement(value: Any?): JsonElement = when (value) {
        null -> JsonNull.INSTANCE
        is JsonElement -> value
        is Document -> value.entries.fold(JsonObject()) { jsonObject, entry ->
            jsonObject.apply { add(entry.key, bsonValueToJsonElement(entry.value)) }
        }
        is Map<*, *> -> value.entries.fold(JsonObject()) { jsonObject, entry ->
            val key = entry.key as? String ?: return@fold jsonObject
            jsonObject.apply { add(key, bsonValueToJsonElement(entry.value)) }
        }
        is Iterable<*> -> {
            val array = com.google.gson.JsonArray()
            value.forEach { array.add(bsonValueToJsonElement(it)) }
            array
        }
        is Array<*> -> {
            val array = com.google.gson.JsonArray()
            value.forEach { array.add(bsonValueToJsonElement(it)) }
            array
        }
        is Number -> JsonPrimitive(value)
        is Boolean -> JsonPrimitive(value)
        is String -> JsonPrimitive(value)
        is Date -> JsonPrimitive(value.toInstant().toString())
        else -> GsonProvider.gson.toJsonTree(value)
    }

    private fun extractInstant(value: Any?): Instant? = when (value) {
        is Instant -> value
        is Number -> Instant.ofEpochMilli(value.toLong())
        is Date -> value.toInstant()
        is String -> runCatching { Instant.parse(value) }.getOrNull()
        else -> null
    }
}

