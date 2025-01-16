package net.dungeonhub.mojang.connection

import com.google.gson.JsonParser
import net.dungeonhub.cache.memory.HashMapCache
import net.dungeonhub.exception.PlayerNotFoundException
import net.dungeonhub.mojang.entity.Player
import net.dungeonhub.mojang.entity.toPlayer
import okhttp3.OkHttpClient
import okhttp3.Request
import org.slf4j.LoggerFactory
import java.io.IOException
import java.time.Duration
import java.time.Instant
import java.util.*

object MojangConnection {
    private const val EXPIRATION_TIME: Long = 60 * 60 * 3
    private val logger = LoggerFactory.getLogger(MojangConnection::class.java)

    val cache = HashMapCache<Player, UUID> { it.id }

    //TODO add provider
    fun getHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .connectTimeout(Duration.ofSeconds(30))
            .readTimeout(Duration.ofSeconds(30))
            .callTimeout(Duration.ofSeconds(30))
            .writeTimeout(Duration.ofSeconds(30))
            .build()
    }

    @Throws(PlayerNotFoundException::class)
    fun getUUIDByName(name: String): UUID {
        val cachedEntry = cache.retrieveAllElements().firstOrNull { playerElement ->
            if (playerElement.timeAdded.isBefore(Instant.now().minusSeconds(EXPIRATION_TIME))) {
                cache.invalidateEntry(playerElement.value.id)
                return@firstOrNull false
            }

            playerElement.value.name.equals(name, ignoreCase = true)
        }

        if (cachedEntry != null) {
            return cachedEntry.value.id
        }

        val request = Request.Builder()
            .url("https://api.minecraftservices.com/minecraft/profile/lookup/name/$name")
            .get()
            .build()

        try {
            getHttpClient().newCall(request).execute().use { response ->
                if (response.isSuccessful && response.body != null) {
                    val player = JsonParser.parseString(response.body!!.string()).asJsonObject.toPlayer()
                    cache.store(player)
                    return player.id
                }
            }
        } catch (exception: IOException) {
            logger.error(null, exception)
        } catch (exception: NullPointerException) {
            logger.error(null, exception)
        }

        throw PlayerNotFoundException(name)
    }

    @Throws(PlayerNotFoundException::class)
    fun getNameByUUID(uuid: UUID): String {
        val cachedEntry = cache.retrieveAllElements().firstOrNull { playerElement ->
            if (playerElement.timeAdded.isBefore(Instant.now().minusSeconds(EXPIRATION_TIME))) {
                cache.invalidateEntry(playerElement.value.id)
                return@firstOrNull false
            }

            playerElement.value.id == uuid
        }

        if (cachedEntry != null) {
            return cachedEntry.value.name
        }

        val request = Request.Builder()
            .url("https://api.minecraftservices.com/minecraft/profile/lookup/$uuid")
            .get()
            .build()

        try {
            getHttpClient().newCall(request).execute().use { response ->
                if (response.isSuccessful && response.body != null) {
                    val player = JsonParser.parseString(response.body!!.string()).asJsonObject.toPlayer()
                    cache.store(player)
                    return player.name
                }
            }
        } catch (exception: IOException) {
            logger.error(null, exception)
        } catch (exception: NullPointerException) {
            logger.error(null, exception)
        }

        throw PlayerNotFoundException(uuid)
    }
}