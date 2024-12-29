package net.dungeonhub.hypixel.connection

import com.google.gson.*
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.utils.io.jvm.javaio.*
import kotlinx.coroutines.future.future
import kotlinx.coroutines.runBlocking
import net.dungeonhub.exception.FailedToLoadException
import net.hypixel.api.HypixelAPI
import net.hypixel.api.http.HypixelHttpClient
import net.hypixel.api.http.HypixelHttpResponse
import net.hypixel.api.http.RateLimit
import net.hypixel.api.reply.StatusReply
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.jsoup.Jsoup
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.time.Duration
import java.util.*
import java.util.concurrent.CompletableFuture

object HypixelConnection : HypixelHttpClient {
    var apiKey: String? = System.getenv("HYPIXEL_API_KEY")

    private val logger: Logger = LoggerFactory.getLogger(HypixelConnection::class.java)

    //TODO provider
    private val httpClient: OkHttpClient = OkHttpClient.Builder()
        .retryOnConnectionFailure(true)
        .connectTimeout(Duration.ofSeconds(30))
        .readTimeout(Duration.ofSeconds(30))
        .callTimeout(Duration.ofSeconds(30))
        .writeTimeout(Duration.ofSeconds(30))
        .build()

    private val client = HttpClient()

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
                httpClient.newCall(request).execute().use { response ->
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

    fun getSkyCryptDataSync(ign: String): Map<String, String> {
        return runBlocking {
            future {
                getSkyCryptData(ign)
            }
        }.join()
    }

    suspend fun getSkyCryptData(ign: String): Map<String, String> {
        val result: MutableMap<String, String> = HashMap()
        val url = "https://sky.shiiyu.moe/stats/$ign"

        val response = client.get {
            url(url)
        }

        BufferedReader(InputStreamReader(response.bodyAsChannel().toInputStream())).use { reader ->
            val content = StringBuilder()
            var line: String

            while ((reader.readLine().also { line = it }) != null) {
                content.append(line)
                content.append(System.lineSeparator())

                if (line.equals("</head>", ignoreCase = true) || line.contains("</head>")) {
                    break
                }
            }

            val document = Jsoup.parse(content.toString())

            val head = document.head()
            for (meta in head.getElementsByTag("meta")) {
                when (meta.attr("property").lowercase(Locale.getDefault())) {
                    "og:title" -> result["title"] = meta.attr("content")
                    "og:image" -> result["icon"] = meta.attr("content")
                    "og:description" -> result["description"] = meta.attr("content")
                }
            }
        }

        if (result.getOrDefault("title", "SkyBlock Stats").equals("SkyBlock Stats", ignoreCase = true)) {
            return HashMap()
        }

        return result
    }

    fun getOnlineStatus(uuid: UUID): StatusReply.Session {
        return hypixelApi.getStatus(uuid).join().session
    }
}