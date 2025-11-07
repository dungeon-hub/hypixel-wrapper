package net.dungeonhub

import com.google.gson.reflect.TypeToken
import com.mongodb.MongoClientSettings
import com.mongodb.ServerAddress
import com.mongodb.ServerCursor
import com.mongodb.client.FindIterable
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoCursor
import com.mongodb.client.MongoIterable
import com.mongodb.client.model.Collation
import com.mongodb.client.model.CursorType
import com.mongodb.client.model.ExplainVerbosity
import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.InsertOneResult
import net.dungeonhub.cache.database.MongoCache
import net.dungeonhub.cache.memory.CacheElement
import org.bson.Document
import org.bson.BsonObjectId
import org.bson.conversions.Bson
import org.bson.types.ObjectId
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import io.mockk.every
import io.mockk.mockk
import java.time.Instant
import java.util.Spliterator
import java.util.UUID
import java.util.concurrent.TimeUnit
import java.util.function.Function
import com.mongodb.Block
import org.bson.BsonValue
import org.reactivestreams.Subscriber
import com.mongodb.client.MongoBatchCursor

class TestMongoCache {
    private data class SampleEntity(val id: UUID, val name: String)

    private lateinit var storedDocuments: MutableMap<String, MutableList<Document>>
    private lateinit var collection: MongoCollection<Document>
    private lateinit var cache: MongoCache<SampleEntity, UUID>

    private val codecRegistry = MongoClientSettings.getDefaultCodecRegistry()

    @BeforeEach
    fun setup() {
        storedDocuments = mutableMapOf()
        collection = mockk(relaxed = true)

        every { collection.withDocumentClass(Document::class.java) } returns collection

        every { collection.insertOne(any<Document>()) } answers {
            val document = firstArg<Document>()
            val key = document.getString(KEY_FIELD) ?: ObjectId().toHexString()
            val history = storedDocuments.getOrPut(key) { mutableListOf() }
            history.add(Document(document))
            InsertOneResult.acknowledged(BsonObjectId(ObjectId()))
        }

        every { collection.find(any<Bson>()) } answers {
            val key = extractKey(firstArg())
            val documents = key?.let { storedDocuments[it] }?.map(::Document) ?: emptyList()
            SimpleFindIterable(documents)
        }

        every { collection.find() } answers {
            val documents = storedDocuments.values.flatten().map(::Document)
            SimpleFindIterable(documents)
        }

        every { collection.deleteMany(any<Bson>()) } answers {
            val key = extractKey(firstArg())
            val removed = if (key != null) {
                val removedHistory = storedDocuments.remove(key)
                removedHistory?.size?.toLong() ?: 0L
            } else {
                0L
            }
            DeleteResult.acknowledged(removed)
        }

        cache = MongoCache(
            collection = collection,
            typeToken = object : TypeToken<CacheElement<SampleEntity>>() {},
            keyFunction = SampleEntity::id,
            keySerializer = { it.toString() }
        )
    }

    @Test
    fun `stores and retrieves cache entries`() {
        val entity = SampleEntity(UUID.randomUUID(), "playerOne")

        assertNull(cache.retrieve(entity.id))

        cache.store(entity)

        val cachedElement = cache.retrieveElement(entity.id)
        assertNotNull(cachedElement)
        assertEquals(entity, cachedElement.value)

        val cached = cache.retrieve(entity.id)
        assertEquals(entity, cached)

        val countAfterStore = cache.retrieveAllElements().use { it.count() }
        assertEquals(1, countAfterStore)

        cache.invalidateEntry(entity.id)

        assertNull(cache.retrieve(entity.id))
        val countAfterInvalidate = cache.retrieveAllElements().use { it.count() }
        assertEquals(0, countAfterInvalidate)
    }

    @Test
    fun `maintains history and returns most recent entry`() {
        val key = UUID.randomUUID()
        val first = SampleEntity(key, "first")
        val second = SampleEntity(key, "second")

        cache.store(first)
        cache.store(second)

        val history = storedDocuments[key.toString()]
        assertNotNull(history)
        assertEquals(2, history.size)

        val latestTimestamp = history.lastOrNull()?.get(TIMESTAMP_FIELD) as? Instant
        if (latestTimestamp != null && history.size >= 2) {
            history[0][TIMESTAMP_FIELD] = latestTimestamp.minusSeconds(1)
        }

        val cached = cache.retrieve(key)
        assertEquals(second, cached)
    }

    @Test
    fun `ignores malformed cache payloads`() {
        val key = UUID.randomUUID()
        storedDocuments[key.toString()] = mutableListOf(
            Document(
                mapOf(
                    KEY_FIELD to key.toString(),
                    TIMESTAMP_FIELD to Instant.now(),
                    VALUE_FIELD to "{"
                )
            )
        )

        assertNull(cache.retrieveElement(key))

        val countAfterCorruption = cache.retrieveAllElements().use { it.count() }
        assertEquals(0, countAfterCorruption)
    }

    private fun extractKey(filter: Bson): String? {
        val bsonDocument = filter.toBsonDocument(Document::class.java, codecRegistry)
        val idValue = bsonDocument.get(KEY_FIELD) ?: return null
        return when {
            idValue.isString -> idValue.asString().value
            else -> idValue.toString()
        }
    }

    private class SimpleFindIterable(private val documents: List<Document>) : FindIterable<Document> {
        override fun first(): Document? = documents.firstOrNull()

        override fun iterator(): MongoCursor<Document> = SimpleMongoCursor(documents.iterator())

        override fun cursor(): MongoCursor<Document> = iterator()

        override fun spliterator(): Spliterator<Document> = documents.spliterator()

        override fun forEach(block: Block<in Document>) {
            documents.forEach(block::apply)
        }

        override fun into(target: MutableCollection<in Document>): MutableCollection<in Document> {
            target.addAll(documents)
            return target
        }

        override fun <U : Any?> map(mapper: Function<in Document, out U>): MongoIterable<U> {
            val mapped = documents.map { mapper.apply(it) }
            return SimpleMongoIterable(mapped)
        }

        override fun subscribe(subscriber: Subscriber<in Document>) {
            subscriber.onSubscribe(SimpleSubscription())
            documents.forEach(subscriber::onNext)
            subscriber.onComplete()
        }

        override fun batchCursor(): MongoBatchCursor<Document> {
            throw UnsupportedOperationException("batchCursor is not supported in tests")
        }

        override fun filter(filter: Bson): FindIterable<Document> = this

        override fun limit(limit: Int): FindIterable<Document> = this

        override fun skip(skip: Int): FindIterable<Document> = this

        override fun maxTime(maxTime: Long, timeUnit: TimeUnit): FindIterable<Document> = this

        override fun maxAwaitTime(maxAwaitTime: Long, timeUnit: TimeUnit): FindIterable<Document> = this

        override fun modifiers(modifiers: Bson): FindIterable<Document> = this

        override fun projection(projection: Bson): FindIterable<Document> = this

        override fun sort(sort: Bson): FindIterable<Document> {
            val sortDocument = sort.toBsonDocument(Document::class.java, MongoClientSettings.getDefaultCodecRegistry())
            val timestampOrder = sortDocument.get(TIMESTAMP_FIELD)?.asInt32()?.value ?: 0
            val sorted = when {
                timestampOrder < 0 -> documents.sortedByDescending { it.extractTimestamp() }
                timestampOrder > 0 -> documents.sortedBy { it.extractTimestamp() }
                else -> documents
            }
            return SimpleFindIterable(sorted)
        }

        private fun Document.extractTimestamp(): Instant {
            val value = get(TIMESTAMP_FIELD)
            return when (value) {
                is Instant -> value
                is Number -> Instant.ofEpochMilli(value.toLong())
                is java.util.Date -> value.toInstant()
                is String -> runCatching { Instant.parse(value) }.getOrDefault(Instant.MIN)
                else -> Instant.MIN
            }
        }

        override fun noCursorTimeout(noCursorTimeout: Boolean): FindIterable<Document> = this

        override fun oplogReplay(oplogReplay: Boolean): FindIterable<Document> = this

        override fun partial(partial: Boolean): FindIterable<Document> = this

        override fun cursorType(cursorType: CursorType): FindIterable<Document> = this

        override fun collation(collation: Collation): FindIterable<Document> = this

        override fun comment(comment: String): FindIterable<Document> = this

        override fun comment(comment: BsonValue): FindIterable<Document> = this

        override fun hint(hint: Bson): FindIterable<Document> = this

        override fun hintString(hint: String): FindIterable<Document> = this

        override fun max(max: Bson): FindIterable<Document> = this

        override fun min(min: Bson): FindIterable<Document> = this

        override fun returnKey(returnKey: Boolean): FindIterable<Document> = this

        override fun showRecordId(showRecordId: Boolean): FindIterable<Document> = this

        override fun batchSize(batchSize: Int): FindIterable<Document> = this

        override fun allowDiskUse(allowDiskUse: Boolean?): FindIterable<Document> = this

        override fun explain(): Document = Document()

        override fun explain(verbosity: ExplainVerbosity): Document = Document()

        override fun <TDocument : Any?> explain(resultClass: Class<TDocument>): TDocument {
            throw UnsupportedOperationException("explain with resultClass is not supported in tests")
        }

        override fun <TDocument : Any?> explain(resultClass: Class<TDocument>, verbosity: ExplainVerbosity): TDocument {
            throw UnsupportedOperationException("explain with resultClass is not supported in tests")
        }
    }

    private class SimpleMongoIterable<T>(private val items: List<T>) : MongoIterable<T> {
        override fun iterator(): MongoCursor<T> = SimpleMongoCursor(items.iterator())

        override fun cursor(): MongoCursor<T> = iterator()

        override fun first(): T? = items.firstOrNull()

        override fun forEach(block: Block<in T>) {
            items.forEach(block::apply)
        }

        override fun <U : Any?> map(mapper: Function<in T, out U>): MongoIterable<U> {
            val mapped = items.map { mapper.apply(it) }
            return SimpleMongoIterable(mapped)
        }

        override fun into(target: MutableCollection<in T>): MutableCollection<in T> {
            target.addAll(items)
            return target
        }

        override fun spliterator(): Spliterator<T> = items.spliterator()

        override fun subscribe(subscriber: Subscriber<in T>) {
            subscriber.onSubscribe(SimpleSubscription())
            items.forEach(subscriber::onNext)
            subscriber.onComplete()
        }

        override fun batchCursor(): MongoBatchCursor<T> {
            throw UnsupportedOperationException("batchCursor is not supported in tests")
        }
    }

    private class SimpleMongoCursor<T>(iterator: Iterator<T>) : MongoCursor<T> {
        private val delegate = iterator

        override fun hasNext(): Boolean = delegate.hasNext()

        override fun next(): T = delegate.next()

        override fun tryNext(): T? = if (delegate.hasNext()) delegate.next() else null

        override fun close() {}

        override fun remove() {
            throw UnsupportedOperationException("remove is not supported")
        }

        override fun available(): Int = 0

        override fun serverCursor(): ServerCursor? = null

        override fun serverAddress(): ServerAddress? = null
    }

    private class SimpleSubscription : org.reactivestreams.Subscription {
        override fun request(n: Long) {
            // The data is delivered immediately in subscribe
        }

        override fun cancel() {
            // No-op for the simple subscription used in tests
        }
    }

    companion object {
        private const val KEY_FIELD = "key"
        private const val TIMESTAMP_FIELD = "timestamp"
        private const val VALUE_FIELD = "value"
    }
}
