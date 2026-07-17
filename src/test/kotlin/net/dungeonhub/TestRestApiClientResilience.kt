package net.dungeonhub

import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkAll
import net.dungeonhub.hypixel.client.RestApiClient
import net.dungeonhub.hypixel.connection.HypixelConnection
import net.dungeonhub.provider.HttpClientProvider
import net.dungeonhub.service.TestHelper.toMockResponse
import okhttp3.Call
import okhttp3.OkHttpClient
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertDoesNotThrow
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import java.io.IOException
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertNull

class TestRestApiClientResilience {

    private val testUuid = UUID.fromString("39642ffc-a7fb-4d24-a1d4-916f4cad1d98")

    @BeforeEach
    fun setUp() {
        HypixelConnection.apiKey = "test-api-key"
        mockkObject(HttpClientProvider)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    // --- IOException / timeout ---

    @Test
    fun testGetPlayerDataReturnsNullOnIOException() {
        mockHttpClientThrows(IOException("simulated timeout"))
        assertNull(RestApiClient.getPlayerData(testUuid))
    }

    @Test
    fun testFetchSkyblockProfilesReturnsNullOnIOException() {
        mockHttpClientThrows(IOException("simulated timeout"))
        assertNull(RestApiClient.fetchSkyblockProfiles(testUuid))
    }

    @Test
    fun testGetSkyblockProfilesReturnsEmptyOnIOException() {
        mockHttpClientThrows(IOException("simulated timeout"))
        val result = RestApiClient.getSkyblockProfiles(testUuid)
        assertNull(result)
    }

    @Test
    fun testGetSessionReturnsNullOnIOException() {
        mockHttpClientThrows(IOException("simulated timeout"))
        assertNull(RestApiClient.getSession(testUuid))
    }

    @Test
    fun testGetGuildReturnsNullOnIOException() {
        mockHttpClientThrows(IOException("simulated timeout"))
        assertNull(RestApiClient.getGuild("DungeonHub"))
    }

    @Test
    fun testGetPlayerGuildReturnsNullOnIOException() {
        mockHttpClientThrows(IOException("simulated timeout"))
        assertNull(RestApiClient.getPlayerGuild(testUuid))
    }

    @Test
    fun testGetBingoDataReturnsNullOnIOException() {
        mockHttpClientThrows(IOException("simulated timeout"))
        assertNull(RestApiClient.getBingoData(testUuid))
    }

    @Test
    fun testGetCurrentBingoEventReturnsNullOnIOException() {
        mockHttpClientThrows(IOException("simulated timeout"))
        assertNull(RestApiClient.getCurrentBingoEvent())
    }

    // --- HTTP 522 (CDN error / Hypixel down) ---

    @Test
    fun testGetPlayerDataReturnsNullOn522() {
        mockHttpClientReturns("Bad Gateway".toMockResponse(code = 522, contentType = "text/plain"))
        assertNull(RestApiClient.getPlayerData(testUuid))
    }

    @Test
    fun testFetchSkyblockProfilesReturnsNullOn522() {
        mockHttpClientReturns("Bad Gateway".toMockResponse(code = 522, contentType = "text/plain"))
        assertNull(RestApiClient.fetchSkyblockProfiles(testUuid))
    }

    @Test
    fun testGetSessionReturnsNullOn522() {
        mockHttpClientReturns("Bad Gateway".toMockResponse(code = 522, contentType = "text/plain"))
        assertNull(RestApiClient.getSession(testUuid))
    }

    @Test
    fun testGetGuildReturnsNullOn522() {
        mockHttpClientReturns("Bad Gateway".toMockResponse(code = 522, contentType = "text/plain"))
        assertNull(RestApiClient.getGuild("DungeonHub"))
    }

    @Test
    fun testGetBingoDataReturnsNullOn522() {
        mockHttpClientReturns("Bad Gateway".toMockResponse(code = 522, contentType = "text/plain"))
        assertNull(RestApiClient.getBingoData(testUuid))
    }

    // --- Missing rate-limit headers on 200 ---

    @Test
    fun testMakeAuthenticatedRequestDoesNotThrowOnMissingRateLimitHeaders() {
        // A 200 response with no RateLimit-* headers previously caused NPE in createRateLimitResponse;
        // the fix makes it return null instead. The method should complete without throwing.
        mockHttpClientReturns("""{"success":true,"session":{"online":false}}""".toMockResponse(code = 200))
        assertDoesNotThrow { RestApiClient.getSession(testUuid) }
    }

    // --- Helpers ---

    private fun mockHttpClientThrows(exception: Exception) {
        every { HttpClientProvider.httpClient }.returns(
            mock<OkHttpClient> {
                on { newCall(any()) }.thenAnswer {
                    mock<Call> {
                        on { execute() }.thenThrow(exception)
                    }
                }
            }
        )
    }

    private fun mockHttpClientReturns(response: okhttp3.Response) {
        every { HttpClientProvider.httpClient }.returns(
            mock<OkHttpClient> {
                on { newCall(any()) }.thenAnswer {
                    mock<Call> {
                        on { execute() }.thenReturn(response)
                    }
                }
            }
        )
    }
}
