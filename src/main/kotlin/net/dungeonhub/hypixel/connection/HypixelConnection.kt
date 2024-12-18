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
import kotlin.math.max

object HypixelConnection : HypixelHttpClient {
    var apiKey: String? = System.getenv("HYPIXEL_API_KEY")

    private val logger: Logger = LoggerFactory.getLogger(HypixelConnection::class.java)

    private val requiredXp: IntArray = intArrayOf(
        50, 125, 235, 395, 625, 955, 1425, 2095, 3045, 4385, 6275, 8940, 12700,
        17960, 25340, 35640, 50040, 70040, 97640, 135640, 188140, 259640, 356640, 488640, 668640, 911640, 1239640,
        1684640, 2284640, 3084640, 4149640, 5559640, 7459640, 9959640, 13259640, 17559640, 23159640, 30359640,
        39559640, 51559640, 66559640, 85559640, 109559640, 139559640, 177559640, 225559640, 285559640, 360559640,
        453559640, 569809640
    )

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

    //This is a request on the Hypixel API, and therefore unneccessary calls should be avoided
    fun getCataLevelByUUID(uuid: UUID): Int {
        val profiles = getProfiles(uuid) ?: return 0

        //Highest cata xp of all profiles
        var highestXP = 0.0

        for (i in 0 until profiles.size()) {
            try {
                val thisXP = profiles[i].asJsonObject
                    .getAsJsonObject("members")[uuid.toString().replace("-", "")]
                    .asJsonObject
                    .getAsJsonObject("dungeons")
                    .getAsJsonObject("dungeon_types")
                    .getAsJsonObject("catacombs")["experience"]
                    .asDouble
                highestXP = max(highestXP, thisXP)
                // null if profile hasn't entered dungeons
            } catch (ignored: NullPointerException) {
                return 0
            }
        }

        return cataXPToLevel(highestXP)
    }

    fun getProfiles(uuid: UUID): JsonArray? {
        val request: Request = Request.Builder()
            .addHeader("API-Key", apiKey!!)
            .url("https://api.hypixel.net/v2/skyblock/profiles?uuid=$uuid")
            .get()
            .build()
        try {
            httpClient.newCall(request).execute().use { response ->
                if (!response.isSuccessful || response.body == null) {
                    logger.error("Unsuccessful profile request for UUID {}", uuid)
                    return null
                }
                val parsed =
                    JsonParser.parseString(response.body!!.string())

                if (parsed == null || parsed.isJsonNull) {
                    return null
                }

                val root = parsed.asJsonObject

                if (root == null || root.isJsonNull || root["profiles"].isJsonNull) {
                    return null
                }
                return root.getAsJsonArray("profiles")
            }
        } catch (ioException: IOException) {
            logger.error("Profile request for UUID threw an error.", ioException)
        }

        return null
    }

    fun getSkyblockLevelByUUID(uuid: UUID): OptionalInt {
        val profiles = getProfiles(uuid)

        if (profiles == null || profiles.isEmpty) {
            return OptionalInt.empty()
        }

        try {
            return profiles.asList().stream()
                .map { obj: JsonElement -> obj.asJsonObject }
                .map { jsonObject -> jsonObject.getAsJsonObject("members") }
                .filter { Objects.nonNull(it) }
                .map { jsonObject -> jsonObject.getAsJsonObject(uuid.toString().replace("-", "")) }
                .filter { Objects.nonNull(it) }
                .map { jsonObject -> jsonObject.getAsJsonObject("leveling") }
                .filter { Objects.nonNull(it) }
                .map { jsonObject -> jsonObject.getAsJsonPrimitive("experience") }
                .filter { Objects.nonNull(it) }
                .mapToInt { it.asInt }
                .map { operand: Int -> operand / 100 }
                .max()
        } catch (nullPointerException: NullPointerException) {
            logger.error("Skyblock level couldn't be loaded for user with UUID `$uuid`.", nullPointerException)
            return OptionalInt.empty()
        }
    }

    private fun cataXPToLevel(xp: Double): Int {
        for (i in requiredXp.indices) {
            if (requiredXp[i] > xp) return i
        }

        // 50 and everything higher is returned as 50
        return 50
    }
}