package net.dungeonhub.hypixel.connection

import net.dungeonhub.exception.FailedToLoadException
import net.dungeonhub.provider.HttpClientProvider
import net.hypixel.api.HypixelAPI
import net.hypixel.api.http.HypixelHttpClient
import net.hypixel.api.http.HypixelHttpResponse
import net.hypixel.api.http.RateLimit
import net.hypixel.api.reply.StatusReply
import okhttp3.Request
import okhttp3.Response
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.IOException
import java.util.*
import java.util.concurrent.CompletableFuture

object HypixelConnection : HypixelHttpClient {
    var apiKey: String? = System.getenv("HYPIXEL_API_KEY")

    private val logger: Logger = LoggerFactory.getLogger(HypixelConnection::class.java)

    val hypixelApi: HypixelAPI = HypixelAPI(this)

    override fun makeRequest(url: String): CompletableFuture<HypixelHttpResponse> {
        return CompletableFuture<HypixelHttpResponse>().completeAsync {
            val request: Request = Request.Builder()
                .url(url)
                .get()
                .build()
            try {
                HttpClientProvider.httpClient.newCall(request).execute().use { response ->
                    if (response.body != null) {
                        return@completeAsync HypixelHttpResponse(response.code, response.body!!.string(), null)
                    }
                }
            } catch (ioException: IOException) {
                logger.error("Error when performing unauthenticated hypixel request")
            }
            throw FailedToLoadException("Hypixel request wasn't successful.")
        }
    }

    override fun makeAuthenticatedRequest(url: String): CompletableFuture<HypixelHttpResponse> {
        return CompletableFuture<HypixelHttpResponse>().completeAsync {
            val request: Request = Request.Builder()
                .addHeader("API-Key", apiKey!!)
                .url(url)
                .get()
                .build()
            try {
                HttpClientProvider.httpClient.newCall(request).execute().use { response ->
                    if (response.body != null) {
                        return@completeAsync HypixelHttpResponse(
                            response.code, response.body!!.string(),
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
        //not needed, happens separately
    }

    private fun createRateLimitResponse(response: Response): RateLimit? {
        if (response.code != 200) {
            return null
        }

        val limit = response.header("RateLimit-Limit")?.toInt()
        val remaining = response.header("RateLimit-Remaining")?.toInt()
        val reset = response.header("RateLimit-Reset")?.toInt()
        return RateLimit(limit!!, remaining!!, reset!!)
    }

    fun getOnlineStatus(uuid: UUID): StatusReply.Session {
        return hypixelApi.getStatus(uuid).join().session
    }
}