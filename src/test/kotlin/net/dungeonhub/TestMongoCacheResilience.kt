package net.dungeonhub

import com.google.gson.reflect.TypeToken
import com.mongodb.MongoException
import com.mongodb.MongoSocketReadTimeoutException
import com.mongodb.ServerAddress
import com.mongodb.client.AggregateIterable
import com.mongodb.client.FindIterable
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoCursor
import io.mockk.every
import io.mockk.mockk
import net.dungeonhub.cache.database.MongoCache
import net.dungeonhub.cache.memory.CacheElement
import org.bson.Document
import org.bson.conversions.Bson
import org.junit.jupiter.api.assertDoesNotThrow
import java.time.Instant
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class TestMongoCacheResilience {

    private fun buildFaultyCache(): MongoCache<String, String> {
        val inner = mockk<MongoCollection<Document>>()

        // find(Bson) returns a FindIterable; mock it to throw when first() is called
        val faultyFind = mockk<FindIterable<Document>>()
        every { faultyFind.sort(any<Bson>()) } returns faultyFind
        every { faultyFind.first() } throws MongoException("server down")
        every { inner.find(any<Bson>()) } returns faultyFind

        val faultyCursor = mockk<MongoCursor<Document>>()
        every { faultyCursor.hasNext() } throws MongoException("server down")
        every { faultyCursor.close() } returns Unit
        val faultyAggregate = mockk<AggregateIterable<Document>>()
        every { faultyAggregate.iterator() } returns faultyCursor
        every { inner.aggregate(any<List<Bson>>(), Document::class.java) } returns faultyAggregate
        every { inner.insertOne(any<Document>()) } throws MongoException("server down")
        every { inner.deleteMany(any<Bson>()) } throws MongoException("server down")

        val outer = mockk<MongoCollection<Document>>()
        every { outer.withDocumentClass(Document::class.java) } returns inner

        return MongoCache(
            outer,
            object : TypeToken<CacheElement<String>>() {},
            { it }
        )
    }

    @Test
    fun testRetrieveElementReturnsNullWhenMongoDown() {
        val cache = buildFaultyCache()
        assertDoesNotThrow {
            assertNull(cache.retrieveElement("someKey"))
        }
    }

    @Test
    fun testRetrieveAllElementsReturnsEmptyStreamWhenCursorThrows() {
        // cursor.hasNext() throws immediately — no elements yielded
        val cache = buildFaultyCache()
        assertDoesNotThrow {
            assertEquals(0, cache.retrieveAllElements().count())
        }
    }

    @Test
    fun testRetrieveAllElementsStopsGracefullyOnMidIterationFailure() {
        // cursor succeeds on first hasNext()/next() then throws, verifying computeNext catches mid-stream
        val goodDoc = Document().also {
            it["key"] = "k"
            it["timestamp"] = Instant.now()
            it["value"] = "hello"
        }
        val cursor = mockk<MongoCursor<Document>>()
        var callCount = 0
        every { cursor.hasNext() } answers {
            callCount++
            if (callCount == 1) true else throw MongoException("connection lost mid-stream")
        }
        every { cursor.next() } returns goodDoc
        every { cursor.close() } returns Unit
        val aggregate = mockk<AggregateIterable<Document>>()
        every { aggregate.iterator() } returns cursor

        val inner = mockk<MongoCollection<Document>>(relaxed = true)
        every { inner.aggregate(any<List<Bson>>(), Document::class.java) } returns aggregate
        val outer = mockk<MongoCollection<Document>>()
        every { outer.withDocumentClass(Document::class.java) } returns inner

        val cache = MongoCache(
            outer,
            object : TypeToken<CacheElement<String>>() {},
            { it }
        )

        assertDoesNotThrow {
            // stream ends after the error; count may be 0 if the document fails to deserialize,
            // but the important thing is no exception propagates
            assertEquals(1, cache.retrieveAllElements().use { it.count() })
        }
    }

    @Test
    fun testStoreDoesNotThrowWhenMongoDown() {
        val cache = buildFaultyCache()
        assertDoesNotThrow {
            cache.store("hello", waitForInsertion = true)
        }
    }

    @Test
    fun testStoreCacheElementDoesNotThrowWhenMongoDown() {
        val cache = buildFaultyCache()
        assertDoesNotThrow {
            cache.storeCacheElement(CacheElement(Instant.now(), "hello"))
        }
    }

    @Test
    fun testInvalidateEntryDoesNotThrowWhenMongoDown() {
        val cache = buildFaultyCache()
        assertDoesNotThrow {
            cache.invalidateEntry("someKey")
        }
    }

    @Test
    fun testRetrieveElementReturnsNullOnSocketReadTimeout() {
        // MongoSocketReadTimeoutException is thrown when a connection hangs past the read timeout;
        // it is a MongoException subclass and must be caught by the same handler.
        val inner = mockk<MongoCollection<Document>>()
        val timeout = MongoSocketReadTimeoutException("read timed out", ServerAddress(), Exception())
        val faultyFind = mockk<FindIterable<Document>>()
        every { faultyFind.sort(any<Bson>()) } returns faultyFind
        every { faultyFind.first() } throws timeout
        every { inner.find(any<Bson>()) } returns faultyFind

        val outer = mockk<MongoCollection<Document>>()
        every { outer.withDocumentClass(Document::class.java) } returns inner

        val cache = MongoCache(
            outer,
            object : TypeToken<CacheElement<String>>() {},
            { it }
        )

        assertDoesNotThrow {
            assertNull(cache.retrieveElement("someKey"))
        }
    }

    @Test
    fun testInMemoryL2CacheStillServedWhenMongoDown() {
        // Store into the in-memory L2 first via a working cache, then break Mongo;
        // the L2 should still return the value without hitting the collection.
        val inner = mockk<MongoCollection<Document>>(relaxed = true)
        val outer = mockk<MongoCollection<Document>>()
        every { outer.withDocumentClass(Document::class.java) } returns inner

        val cache = MongoCache(
            outer,
            object : TypeToken<CacheElement<String>>() {},
            { it },
            memoryCacheSize = 5
        )

        // populate the in-memory L2 by calling store (insert call will be a relaxed no-op)
        cache.store("hello", waitForInsertion = true)

        // now make Mongo blow up for reads
        val faultyFind = mockk<FindIterable<Document>>()
        every { faultyFind.sort(any<Bson>()) } returns faultyFind
        every { faultyFind.first() } throws MongoException("server down")
        every { inner.find(any<Bson>()) } returns faultyFind

        // the L2 should shadow the broken Mongo lookup
        assertDoesNotThrow {
            assertEquals("hello", cache.retrieve("hello"))
        }
    }
}
