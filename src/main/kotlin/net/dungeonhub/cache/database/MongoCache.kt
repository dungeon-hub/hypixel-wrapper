package net.dungeonhub.cache.database

import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Accumulators
import com.mongodb.client.model.Aggregates
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Sorts
import net.dungeonhub.cache.Cache
import net.dungeonhub.cache.memory.CacheElement
import net.dungeonhub.provider.GsonProvider
import org.bson.Document
import java.time.Duration
import java.time.Instant
import java.util.Date
import java.util.Spliterators
import java.util.stream.Stream
import java.util.stream.StreamSupport
import kotlin.concurrent.thread

class MongoCache<T, K>(
    collection: MongoCollection<Document>,
    private val typeToken: TypeToken<CacheElement<T>>,
    private val keyFunction: (T) -> K,
    private val keySerializer: (K) -> String = { it.toString() },
    private val memoryCacheSize: Int = 5,
    private val memoryCacheTtl: Duration? = null
) : Cache<T, K> {
    private val collection: MongoCollection<Document> = collection.withDocumentClass(Document::class.java)
    private val cacheLock = Any()
    private val secondLevelCache = object : LinkedHashMap<K, LocalCacheEntry<T>>(memoryCacheSize.coerceAtLeast(1), 0.75f, true) {
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<K, LocalCacheEntry<T>>?): Boolean {
            return memoryCacheSize in 1..<size
        }
    }

    override fun retrieveElement(key: K): CacheElement<T>? {
        getFromMemoryCache(key)?.let { return it }

        val keyString = keySerializer(key)
        val document = collection
            .find(Filters.eq(KEY_FIELD, keyString))
            .sort(Sorts.descending(TIMESTAMP_FIELD))
            .first() ?: return null
        val element = deserialize(document)
        if (element != null) {
            storeInMemoryCache(key, element)
        }
        return element
    }

    override fun retrieveAllElements(): Stream<CacheElement<T>> {
        val pipeline = listOf(
            Aggregates.sort(Sorts.descending(TIMESTAMP_FIELD)),
            Aggregates.group("$${KEY_FIELD}", Accumulators.first("doc", "$\$ROOT")),
            Aggregates.replaceRoot("\$doc")
        )

        val cursor = collection.aggregate(pipeline, Document::class.java).iterator()

        val iterator = object : Iterator<CacheElement<T>> {
            private var nextComputed = false
            private var nextValue: CacheElement<T>? = null

            private fun computeNext() {
                while (cursor.hasNext()) {
                    val doc = cursor.next()
                    val element = deserialize(doc)
                    if (element != null) {
                        nextValue = element
                        nextComputed = true
                        return
                    }
                }
                nextValue = null
                nextComputed = true
            }

            override fun hasNext(): Boolean {
                if (!nextComputed) computeNext()
                return nextValue != null
            }

            override fun next(): CacheElement<T> {
                if (!nextComputed) computeNext()
                val result = nextValue ?: throw NoSuchElementException()
                nextComputed = false
                nextValue = null
                return result
            }
        }

        val spliterator = Spliterators.spliteratorUnknownSize(iterator, 0)
        val stream = StreamSupport.stream(spliterator, false)

        return stream.onClose { cursor.close() }
    }

    override fun store(value: T, waitForInsertion: Boolean) {
        val key = keyFunction(value)
        val serializedKey = keySerializer(key)
        val timestamp = Instant.now()
        val valueElement = GsonProvider.gson.toJsonTree(value)
        val document = Document()
        document[KEY_FIELD] = serializedKey
        document[TIMESTAMP_FIELD] = timestamp
        document[VALUE_FIELD] = jsonElementToBsonValue(valueElement)
        storeInMemoryCache(key, CacheElement(timestamp, value))
        val insertionThread = thread(start = true) { collection.insertOne(document) }
        if (waitForInsertion) insertionThread.join()
    }

    override fun invalidateEntry(key: K) {
        val serializedKey = keySerializer(key)
        collection.deleteMany(Filters.eq(KEY_FIELD, serializedKey))
        invalidateMemoryCache(key)
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

    private fun getFromMemoryCache(key: K): CacheElement<T>? {
        if (memoryCacheSize <= 0) return null

        val entry = synchronized(cacheLock) {
            val cached = secondLevelCache[key]
            if (cached != null && isExpired(cached)) {
                secondLevelCache.remove(key)
                null
            } else {
                cached
            }
        }

        return entry?.element
    }

    private fun storeInMemoryCache(key: K, element: CacheElement<T>) {
        if (memoryCacheSize <= 0) return
        val entry = LocalCacheEntry(Instant.now(), element)
        synchronized(cacheLock) {
            secondLevelCache[key] = entry
        }
    }

    private fun invalidateMemoryCache(key: K) {
        if (memoryCacheSize <= 0) return
        synchronized(cacheLock) {
            secondLevelCache.remove(key)
        }
    }

    private fun isExpired(entry: LocalCacheEntry<T>): Boolean {
        val ttl = memoryCacheTtl ?: return false
        val now = Instant.now()
        return now.isAfter(entry.cachedAt.plus(ttl))
    }

    companion object {
        private const val KEY_FIELD = "key"
        private const val TIMESTAMP_FIELD = "timestamp"
        private const val VALUE_FIELD = "value"
    }

    private data class LocalCacheEntry<T>(val cachedAt: Instant, val element: CacheElement<T>)

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
