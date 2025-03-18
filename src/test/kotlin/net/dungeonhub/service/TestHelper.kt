package net.dungeonhub.service

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import net.dungeonhub.hypixel.client.DiskCacheApiClient
import net.dungeonhub.hypixel.entities.guild.Guild
import net.dungeonhub.hypixel.entities.guild.toGuild
import net.dungeonhub.hypixel.entities.museum.MuseumData
import net.dungeonhub.hypixel.entities.museum.toMuseumData
import net.dungeonhub.hypixel.entities.player.HypixelPlayer
import net.dungeonhub.hypixel.entities.player.toHypixelPlayer
import net.dungeonhub.hypixel.entities.skyblock.SkyblockProfile
import net.dungeonhub.hypixel.entities.skyblock.SkyblockProfiles
import net.dungeonhub.hypixel.entities.skyblock.toSkyblockProfile
import net.dungeonhub.provider.GsonProvider
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import java.util.stream.Stream
import kotlin.io.path.name
import kotlin.io.path.nameWithoutExtension

object TestHelper {
    fun readFile(fileName: String): String {
        return javaClass.classLoader.getResourceAsStream(fileName)!!.reader(StandardCharsets.UTF_8).readText()
    }

    fun String.toMockResponse(): Response {
        return Response.Builder()
            .request(Request.Builder().url("https://example.com").build())
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("")
            .body(
                toResponseBody("application/json".toMediaTypeOrNull())
            )
            .build()
    }

    fun readFullSkyblockProfile(): List<SkyblockProfile> {
        val fullProfilesJson = readFile("full-profiles/full_skyblock_profiles.json")

        return GsonProvider.gson.fromJson(fullProfilesJson, JsonArray::class.java).map { it.toSkyblockProfile() }
    }

    fun readAllSkyblockProfileObjects(): Stream<SkyblockProfiles> {
        val profilesDirectory = javaClass.classLoader.getResource("full-profiles/")!!.toURI()

        return Files.list(Paths.get(profilesDirectory)).toList().stream().filter {
            try {
                UUID.fromString(it.name.replace(".json", ""))
                return@filter true
            } catch (_: Exception) {
                return@filter false
            }
        }.map { file ->
            val fullProfilesJson = readFile("full-profiles/${file.name}")

            val uuid = UUID.fromString(file.name.replace(".json", ""))

            uuid to GsonProvider.gson.fromJson(fullProfilesJson, JsonArray::class.java).map {
                it.toSkyblockProfile()
            }
        }.map { (key, value) -> SkyblockProfiles(key, value) }
    }

    fun readAllSkyblockProfiles(): Stream<List<SkyblockProfile>> {
        val profilesDirectory = javaClass.classLoader.getResource("full-profiles/")!!.toURI()

        return Files.list(Paths.get(profilesDirectory)).toList().stream().map { file ->
            val fullProfilesJson = readFile("full-profiles/${file.name}")

            GsonProvider.gson.fromJson(fullProfilesJson, JsonArray::class.java).map { it.toSkyblockProfile() }
        }
    }

    fun readAllProdSkyblockProfiles(): Stream<Pair<UUID, List<SkyblockProfile>>> {
        return Stream.concat(
            DiskCacheApiClient.skyblockProfilesCache.retrieveAllElements()
                .map { it.value.owner to it.value.profiles },
            DiskCacheApiClient.skyblockProfilesCache.getAllHistoryEntries()
                .flatMap { it.second }.map { it.value.owner to it.value.profiles })
    }

    fun readAllHypixelPlayers(): List<HypixelPlayer> {
        val profilesDirectory = javaClass.classLoader.getResource("player-data/")!!.toURI()

        return Files.list(Paths.get(profilesDirectory)).map { file ->
            val hypixelPlayerJson = readFile("player-data/${file.name}")

            GsonProvider.gson.fromJson(hypixelPlayerJson, JsonObject::class.java).toHypixelPlayer()
        }.toList()
    }

    fun readAllGuilds(): List<Guild> {
        val guildsDirectory = javaClass.classLoader.getResource("guilds/")!!.toURI()

        return Files.list(Paths.get(guildsDirectory)).map { file ->
            val guildJson = readFile("guilds/${file.name}")

            GsonProvider.gson.fromJson(guildJson, JsonObject::class.java).toGuild()
        }.toList()
    }

    fun readAllMuseumData(): List<MuseumData> {
        val museumDirectory = javaClass.classLoader.getResource("museum/")!!.toURI()

        return Files.list(Paths.get(museumDirectory)).map { file ->
            val museumJson = readFile("museum/${file.name}")

            GsonProvider.gson.fromJson(museumJson, JsonObject::class.java)
                .toMuseumData(UUID.fromString(file.nameWithoutExtension))
        }.toList()
    }

    fun readItemList(): List<JsonObject> {
        return GsonProvider.gson.fromJson(readFile("resources/skyblock_items.json"), JsonObject::class.java)
            .getAsJsonArray("items").map { it.asJsonObject }
    }
}