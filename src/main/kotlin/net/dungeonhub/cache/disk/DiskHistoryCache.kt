package net.dungeonhub.cache.disk

import com.google.gson.reflect.TypeToken
import net.dungeonhub.cache.Cache
import net.dungeonhub.cache.mapNotNull
import net.dungeonhub.cache.memory.CacheElement
import net.dungeonhub.provider.GsonProvider
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.time.Instant
import java.util.stream.Stream
import kotlin.io.path.exists
import kotlin.io.path.isDirectory
import kotlin.io.path.isRegularFile
import kotlin.io.path.name

class DiskHistoryCache<T, K>(
    private val name: String,
    private val typeToken: TypeToken<CacheElement<T>>,
    private val keyFunction: (T) -> K,
    private val keyParser: (String) -> K?
) : Cache<T, K> {
    private val dataDirectory: Path = Path.of(cacheDirectory, name)
    private val historyDirectory: Path = Path.of(cacheDirectory, "history", name)

    init {
        Files.createDirectories(dataDirectory)
        Files.createDirectories(historyDirectory)
    }

    private fun getDataDirectory(key: K): Path {
        val dir = dataDirectory.resolve(key.toString())
        if (!dir.exists()) Files.createDirectories(dir)
        return dir
    }

    private fun getHistoryDirectory(key: K): Path {
        val dir = historyDirectory.resolve(key.toString())
        if (!dir.exists()) Files.createDirectories(dir)
        return dir
    }

    private fun getDataFile(key: K): Path = getDataDirectory(key).resolve("data.json")

    private fun getHistoryFile(key: K, instant: Instant): Path =
        getHistoryDirectory(key).resolve("${instant.toEpochMilli()}.json")

    override fun retrieveElement(key: K): CacheElement<T>? {
        val file = getDataFile(key)
        if (!file.isRegularFile()) return null
        return GsonProvider.gson.fromJson(Files.readString(file), typeToken.type)
    }

    override fun retrieveAllElements(): Stream<CacheElement<T>> {
        return Files.list(dataDirectory)
            .filter { it.isDirectory() }
            .mapNotNull { dir -> keyParser(dir.name)?.let(this::retrieveElement) }
    }

    override fun store(value: T, waitForInsertion: Boolean) {
        val key = keyFunction(value)
        invalidateEntry(key)
        val file = getDataFile(key)
        Files.writeString(file, GsonProvider.gson.toJson(CacheElement(Instant.now(), value)))
    }

    override fun invalidateEntry(key: K) {
        val element = retrieveElement(key) ?: return
        val file = getDataFile(key)
        if (file.isRegularFile()) {
            Files.move(file, getHistoryFile(key, element.timeAdded), StandardCopyOption.REPLACE_EXISTING)
        }
    }

    fun clearAllHistory() {
        deleteDirectoryContents(historyDirectory)
    }

    fun clearHistory(key: K) {
        deleteDirectoryContents(getHistoryDirectory(key))
    }

    companion object {
        var cacheDirectory =
            "${System.getProperty("user.home")}${File.separator}dungeon-hub${File.separator}hypixel-wrapper-cache"

        fun deleteDirectoryContents(root: Path) {
            if (!root.exists()) return
            Files.walk(root).use { walk ->
                walk.sorted(Comparator.reverseOrder())
                    .forEach { Files.deleteIfExists(it) }
            }
        }
    }
}
