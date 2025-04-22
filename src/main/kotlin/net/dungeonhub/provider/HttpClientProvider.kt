package net.dungeonhub.provider

import okhttp3.OkHttpClient
import java.time.Duration

object HttpClientProvider {
    val httpClient: OkHttpClient = OkHttpClient.Builder()
        .retryOnConnectionFailure(true)
        .connectTimeout(Duration.ofSeconds(30))
        .readTimeout(Duration.ofSeconds(30))
        .callTimeout(Duration.ofSeconds(30))
        .writeTimeout(Duration.ofSeconds(30))
        .build()
}