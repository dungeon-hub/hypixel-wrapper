package net.dungeonhub.cache.disk

import com.google.gson.reflect.TypeToken
import net.dungeonhub.cache.Cache
import net.dungeonhub.cache.memory.CacheElement
import net.dungeonhub.provider.GsonProvider
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.time.Instant
import java.util.*
import kotlin.io.path.exists
import kotlin.io.path.isDirectory
import kotlin.io.path.isRegularFile
import kotlin.io.path.name


class DiskHistoryCache<T>(val type: TypeToken<CacheElement<T>>, val keyFunction: (T) -> UUID) : Cache<T, UUID> {
    fun getDirectory(uuid: UUID): Path {
        val root = Path.of(cacheDirectory)
        if (!root.exists()) {
            Files.createDirectory(root)
        }

        val directory = root.resolve(uuid.toString())
        if (!directory.exists()) {
            Files.createDirectory(directory)
        }
        return directory
    }

    fun getDataFile(uuid: UUID): Path {
        val dataFile = getDirectory(uuid).resolve("data.json")
        if (!dataFile.exists()) {
            Files.createFile(dataFile)
        }
        return dataFile
    }

    fun getHistoryDirectory(): Path {
        val historyDirectory = Path.of(cacheDirectory, "history")
        if (!historyDirectory.exists()) {
            Files.createDirectory(historyDirectory)
        }
        return historyDirectory
    }

    fun getHistoryDirectory(uuid: UUID): Path {
        val historyDirectory = getHistoryDirectory().resolve(uuid.toString())
        if (!historyDirectory.exists()) {
            Files.createDirectory(historyDirectory)
        }
        return historyDirectory
    }

    fun getHistoryFile(uuid: UUID, instant: Instant): Path {
        return getHistoryDirectory(uuid).resolve(instant.toEpochMilli().toString())
    }

    override fun retrieveElement(uuid: UUID): CacheElement<T>? {
        val dataFile = getDataFile(uuid)

        if (dataFile.isRegularFile()) {
            val json = Files.readString(dataFile)

            return GsonProvider.gson.fromJson(json, type.type)
        }

        return null
    }

    override fun retrieveAllElements(): List<CacheElement<T>> {
        val cacheDirectory = Path.of(cacheDirectory)

        if (!cacheDirectory.exists()) {
            return emptyList()
        }

        return Files.list(cacheDirectory).map {
            if (it.isDirectory()) {
                val uuid = try {
                    UUID.fromString(it.name)
                } catch (_: IllegalArgumentException) {
                    return@map null
                }

                return@map retrieveElement(uuid)
            }

            return@map null
        }.toList().filterNotNull()
    }

    override fun invalidateEntry(uuid: UUID) {
        val cacheElement = retrieveElement(uuid)
        val dataFile = getDataFile(uuid)

        if (cacheElement != null && dataFile.isRegularFile()) {
            Files.move(dataFile, getHistoryFile(uuid, cacheElement.timeAdded))
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

    fun clearHistoryDirectory(uuid: UUID) {
        deleteDirectoryWithContents(getHistoryDirectory(uuid))
    }

    companion object {
        var cacheDirectory =
            "${System.getProperty("user.home")}${File.separator}dungeon-hub${File.separator}hypixel-wrapper-cache"

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