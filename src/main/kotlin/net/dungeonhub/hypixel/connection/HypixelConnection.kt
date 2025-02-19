package net.dungeonhub.hypixel.connection

import net.dungeonhub.exception.FailedToLoadException
import net.hypixel.api.HypixelAPI
import net.hypixel.api.http.HypixelHttpClient
import net.hypixel.api.http.HypixelHttpResponse
import net.hypixel.api.http.RateLimit
import net.hypixel.api.reply.StatusReply
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.IOException
import java.time.Duration
import java.util.*
import java.util.concurrent.CompletableFuture

object HypixelConnection : HypixelHttpClient {
    const val TIMEOUT_SECONDS = 30L
    const val OK_STATUS_CODE = 200

    var apiKey: String? = System.getenv("HYPIXEL_API_KEY")

    private val logger: Logger = LoggerFactory.getLogger(HypixelConnection::class.java)

    // TODO provider
    private val httpClient: OkHttpClient = OkHttpClient.Builder()
        .retryOnConnectionFailure(true)
        .connectTimeout(Duration.ofSeconds(TIMEOUT_SECONDS))
        .readTimeout(Duration.ofSeconds(TIMEOUT_SECONDS))
        .callTimeout(Duration.ofSeconds(TIMEOUT_SECONDS))
        .writeTimeout(Duration.ofSeconds(TIMEOUT_SECONDS))
        .build()

    val hypixelApi: HypixelAPI = HypixelAPI(this)

    override fun makeRequest(url: String): CompletableFuture<HypixelHttpResponse> {
        return CompletableFuture<HypixelHttpResponse>().completeAsync {
            val request: Request = Request.Builder()
                .url(url)
                .get()
                .build()
            try {
                httpClient.newCall(request).execute().use { response ->
                    if (response.body != null) {
                        return@completeAsync HypixelHttpResponse(response.code, response.body?.string(), null)
                    }
                }
            } catch (_: IOException) {
                logger.error("Error when performing unauthenticated hypixel request")
            }
            throw FailedToLoadException("Hypixel request wasn't successful.")
        }
    }

    override fun makeAuthenticatedRequest(url: String): CompletableFuture<HypixelHttpResponse> {
        val apiKey = apiKey ?: throw IllegalArgumentException("Hypixel API key is required.")

        return CompletableFuture<HypixelHttpResponse>().completeAsync {
            val request: Request = Request.Builder()
                .addHeader("API-Key", apiKey)
                .url(url)
                .get()
                .build()
            try {
                httpClient.newCall(request).execute().use { response ->
                    if (response.body != null) {
                        return@completeAsync HypixelHttpResponse(
                            response.code, response.body?.string(),
                            createRateLimitResponse(response)
                        )
                    }
                }
            } catch (ioException: IOException) {
                logger.error("Error when performing authenticated hypixel request {}.", url, ioException)
            }
            throw FailedToLoadException("Hypixel request wasn't successful.")
        }
    }

    override fun shutdown() {
        // not needed, happens separately
    }

    private fun createRateLimitResponse(response: Response): RateLimit? {
        if (response.code != OK_STATUS_CODE) {
            return null
        }

        val limit = response.header("RateLimit-Limit")?.toInt() ?: return null
        val remaining = response.header("RateLimit-Remaining")?.toInt() ?: return null
        val reset = response.header("RateLimit-Reset")?.toInt() ?: return null
        return RateLimit(limit, remaining, reset)
    }

    fun getOnlineStatus(uuid: UUID): StatusReply.Session = hypixelApi.getStatus(uuid).join().session
}