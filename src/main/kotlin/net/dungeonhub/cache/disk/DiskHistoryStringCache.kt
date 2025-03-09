package net.dungeonhub.cache.disk

import com.google.gson.reflect.TypeToken
import net.dungeonhub.cache.Cache
import net.dungeonhub.cache.memory.CacheElement
import net.dungeonhub.provider.GsonProvider
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.time.Instant
import kotlin.io.path.exists
import kotlin.io.path.isDirectory
import kotlin.io.path.isRegularFile
import kotlin.io.path.name

class DiskHistoryStringCache<T>(
    val name: String,
    val type: TypeToken<CacheElement<T>>,
    val keyFunction: (T) -> String
) : Cache<T, String> {
    fun getDirectory(name: String): Path {
        val directory = Path.of(cacheDirectory, this@DiskHistoryStringCache.name, name.toString())
        if (!directory.exists()) {
            Files.createDirectories(directory)
        }
        return directory
    }

    fun getDataFile(name: String): Path {
        val dataFile = getDirectory(name).resolve("data.json")
        if (!dataFile.exists()) {
            Files.createFile(dataFile)
        }
        return dataFile
    }

    fun getHistoryDirectory(): Path {
        val historyDirectory = Path.of(cacheDirectory, "history", name)
        if (!historyDirectory.exists()) {
            Files.createDirectories(historyDirectory)
        }
        return historyDirectory
    }

    fun getHistoryDirectory(name: String): Path {
        val historyDirectory = getHistoryDirectory().resolve(name.toString())
        if (!historyDirectory.exists()) {
            Files.createDirectory(historyDirectory)
        }
        return historyDirectory
    }

    fun getHistoryFile(name: String, instant: Instant): Path {
        return getHistoryDirectory(name).resolve(instant.toEpochMilli().toString() + ".json")
    }

    override fun retrieveElement(key: String): CacheElement<T>? {
        val dataFile = getDataFile(key)

        if (dataFile.isRegularFile()) {
            val json = Files.readString(dataFile)

            return GsonProvider.gson.fromJson(json, type.type)
        }

        return null
    }

    override fun retrieveAllElements(): List<CacheElement<T>> {
        val cacheDirectory = Path.of(cacheDirectory, name)

        if (!cacheDirectory.exists()) {
            return emptyList()
        }

        return Files.list(cacheDirectory).map {
            if (it.isDirectory()) {
                return@map retrieveElement(it.name)
            }

            return@map null
        }.toList().filterNotNull()
    }

    override fun invalidateEntry(key: String) {
        val cacheElement = retrieveElement(key)
        val dataFile = getDataFile(key)

        if (cacheElement != null && dataFile.isRegularFile()) {
            Files.move(dataFile, getHistoryFile(key, cacheElement.timeAdded))
        }
    }

    override fun store(value: T) {
        val uuid = keyFunction.invoke(value)

        invalidateEntry(uuid)

        Files.writeString(getDataFile(uuid), GsonProvider.gson.toJson(CacheElement(Instant.now(), value)))
    }

    fun clearHistoryDirectory() {
        deleteDirectoryWithContents(getHistoryDirectory())
    }

    fun clearHistoryDirectory(name: String) {
        deleteDirectoryWithContents(getHistoryDirectory(name))
    }

    companion object {
        var cacheDirectory =
            "${System.getProperty("user.home")}${File.separator}dungeon-hub${File.separator}hypixel-wrapper-cache" //TODO make it so that only one of those exists (together with the one in UUID cache)

        fun deleteDirectoryWithContents(rootPath: Path) {
            if (!rootPath.exists()) {
                return
            }

            Files.walk(rootPath).use { walk ->
                walk.sorted(Comparator.reverseOrder())
                    .forEach { obj -> Files.delete(obj) }
            }
        }
    }
}