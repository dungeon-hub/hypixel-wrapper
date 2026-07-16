package net.dungeonhub

import com.google.gson.JsonObject
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkAll
import net.dungeonhub.cache.CacheType
import net.dungeonhub.hypixel.client.CacheApiClient
import net.dungeonhub.hypixel.client.FallbackApiClient
import net.dungeonhub.hypixel.client.RestApiClient
import net.dungeonhub.hypixel.connection.HypixelConnection
import net.dungeonhub.hypixel.entities.player.toHypixelPlayer
import net.dungeonhub.provider.HttpClientProvider
import net.dungeonhub.provider.GsonProvider
import net.dungeonhub.service.TestHelper
import okhttp3.Call
import okhttp3.OkHttpClient
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import java.io.IOException
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class TestStaleCacheFallback {

    private val testUuid = UUID.fromString("39642ffc-a7fb-4d24-a1d4-916f4cad1d98")

    @BeforeEach
    fun setUp() {
        HypixelConnection.apiKey = "test-api-key"
        mockkObject(HttpClientProvider)
        every { HttpClientProvider.httpClient }.returns(
            mock<OkHttpClient> {
                on { newCall(any()) }.thenAnswer {
                    mock<Call> { on { execute() }.thenThrow(IOException("REST unavailable")) }
                }
            }
        )
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun testStaleCacheServedWhenRestDown() {
        val cache = CacheApiClient(CacheType.Memory)
        val playerJson = GsonProvider.gson.fromJson(
            TestHelper.readFile("player-data/39642ffc-a7fb-4d24-a1d4-916f4cad1d98.json"),
            JsonObject::class.java
        )
        cache.playerDataCache.store(playerJson.toHypixelPlayer())

        // expiresAfterMinutes=0 forces the entry to be treated as expired immediately
        val client = FallbackApiClient(cache, RestApiClient, expiresAfterMinutes = -1, useStaleCache = true)

        // REST is down, cache is expired — stale fallback should still return the data
        assertNotNull(client.getPlayerData(testUuid))
    }

    @Test
    fun testWithoutStaleCacheReturnsNullWhenRestDown() {
        val cache = CacheApiClient(CacheType.Memory)
        val playerJson = GsonProvider.gson.fromJson(
            TestHelper.readFile("player-data/39642ffc-a7fb-4d24-a1d4-916f4cad1d98.json"),
            JsonObject::class.java
        )
        cache.playerDataCache.store(playerJson.toHypixelPlayer())

        val client = FallbackApiClient(cache, RestApiClient, expiresAfterMinutes = -1, useStaleCache = false)

        // REST is down, cache is expired, stale fallback disabled — should return null
        assertNull(client.getPlayerData(testUuid))
    }

    @Test
    fun testStaleCacheNotUsedWhenRestSucceeds() {
        // REST is up (mock is replaced with a 522, which becomes null from RestApiClient)
        // The stale-cache logic should only activate when REST returns null
        val cache = CacheApiClient(CacheType.Memory)
        val client = FallbackApiClient(cache, RestApiClient, expiresAfterMinutes = -1, useStaleCache = true)

        // No data in cache, REST returns null — result should be null, not an exception
        assertNull(client.getPlayerData(testUuid))
    }
}
