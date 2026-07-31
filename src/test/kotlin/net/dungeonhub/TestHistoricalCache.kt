package net.dungeonhub

import com.google.gson.reflect.TypeToken
import net.dungeonhub.cache.disk.DiskHistoryCache
import net.dungeonhub.cache.memory.CacheElement
import net.dungeonhub.provider.GsonProvider
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.time.Instant
import kotlin.test.assertEquals
import kotlin.test.assertNull

class TestHistoricalCache {
    private val typeToken = object : TypeToken<CacheElement<HistoricalValue>>() {}

    @Test
    fun testDiskHistoryCacheRetrievesValueBeforeOrAfterTimestamp() {
        val testName = "historical-disk-test-${System.nanoTime()}"
        val dataRoot = java.nio.file.Path.of(DiskHistoryCache.cacheDirectory, testName)
        val historyRoot = java.nio.file.Path.of(DiskHistoryCache.cacheDirectory, "history", testName)
        try {
            val cache = DiskHistoryCache(testName, typeToken, { it.key }, { it })
            val first = CacheElement(Instant.parse("2024-01-01T00:00:00Z"), HistoricalValue("profile", "first"))
            val second = CacheElement(Instant.parse("2024-01-02T00:00:00Z"), HistoricalValue("profile", "second"))
            val third = CacheElement(Instant.parse("2024-01-03T00:00:00Z"), HistoricalValue("profile", "third"))

            writeCacheElement(cache.getHistoryFile("profile", first.timeAdded), first)
            writeCacheElement(cache.getHistoryFile("profile", second.timeAdded), second)
            writeCacheElement(cache.getDataFile("profile"), third)

            assertEquals("second", cache.retrieveAt("profile", before = Instant.parse("2024-01-02T12:00:00Z"))?.payload)
            assertEquals("second", cache.retrieveAt("profile", after = Instant.parse("2024-01-01T12:00:00Z"))?.payload)
            assertEquals(
                "second",
                cache.retrieveAt(
                    "profile",
                    before = Instant.parse("2024-01-02T12:00:00Z"),
                    after = Instant.parse("2024-01-01T12:00:00Z")
                )?.payload
            )
            assertNull(cache.retrieveAt("profile", before = Instant.parse("2023-12-31T23:59:59Z")))
        } finally {
            DiskHistoryCache.deleteDirectoryContents(dataRoot)
            DiskHistoryCache.deleteDirectoryContents(historyRoot)
        }
    }


    private fun writeCacheElement(path: java.nio.file.Path, element: CacheElement<HistoricalValue>) {
        Files.writeString(path, GsonProvider.gson.toJson(element))
    }

    data class HistoricalValue(val key: String, val payload: String)
}
