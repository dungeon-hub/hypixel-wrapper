package net.dungeonhub.cache.database

import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import com.mongodb.client.model.ReplaceOptions
import net.dungeonhub.cache.Cache
import net.dungeonhub.cache.memory.CacheElement
import net.dungeonhub.provider.GsonProvider
import org.bson.Document
import java.time.Instant
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
        val document = collection.find(Filters.eq(ID_FIELD, keyString)).first() ?: return null
        return deserialize(document)
    }

    override fun retrieveAllElements(): Stream<CacheElement<T>> {
        val elements = mutableListOf<CacheElement<T>>()
        collection.find().iterator().use { cursor ->
            while (cursor.hasNext()) {
                val document = cursor.next()
                deserialize(document)?.let(elements::add)
            }
        }
        return elements.stream()
    }

    override fun store(value: T) {
        val key = keyFunction(value)
        val serializedKey = keySerializer(key)
        val cacheElement = CacheElement(timeAdded = Instant.now(), value = value)
        val payload = GsonProvider.gson.toJson(cacheElement)
        val document = Document(mapOf(ID_FIELD to serializedKey, PAYLOAD_FIELD to payload))
        collection.replaceOne(
            Filters.eq(ID_FIELD, serializedKey),
            document,
            ReplaceOptions().upsert(true)
        )
    }

    override fun invalidateEntry(key: K) {
        val serializedKey = keySerializer(key)
        collection.deleteOne(Filters.eq(ID_FIELD, serializedKey))
    }

    private fun deserialize(document: Document): CacheElement<T>? {
        val payload = document.getString(PAYLOAD_FIELD) ?: return null
        return try {
            GsonProvider.gson.fromJson<CacheElement<T>>(payload, typeToken.type)
        } catch (_: JsonSyntaxException) {
            null
        }
    }

    companion object {
        private const val ID_FIELD = "_id"
        private const val PAYLOAD_FIELD = "payload"
    }
}

