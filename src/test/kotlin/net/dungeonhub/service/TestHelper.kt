package net.dungeonhub.service

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import net.dungeonhub.hypixel.entities.skyblock.SkyblockProfile
import net.dungeonhub.hypixel.entities.player.HypixelPlayer
import net.dungeonhub.hypixel.entities.player.toHypixelPlayer
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
import kotlin.io.path.name

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

    fun readAllSkyblockProfiles(): List<List<SkyblockProfile>> {
        val profilesDirectory = javaClass.classLoader.getResource("full-profiles/")!!.toURI()

        return Files.list(Paths.get(profilesDirectory)).map { file ->
            val fullProfilesJson = readFile("full-profiles/${file.name}")

            GsonProvider.gson.fromJson(fullProfilesJson, JsonArray::class.java).map { it.toSkyblockProfile() }
        }.toList()
    }

    fun readAllHypixelPlayers(): List<HypixelPlayer> {
        val profilesDirectory = javaClass.classLoader.getResource("player-data/")!!.toURI()

        return Files.list(Paths.get(profilesDirectory)).map { file ->
            val hypixelPlayerJson = readFile("player-data/${file.name}")

            GsonProvider.gson.fromJson(hypixelPlayerJson, JsonObject::class.java).toHypixelPlayer()
        }.toList()
    }
}