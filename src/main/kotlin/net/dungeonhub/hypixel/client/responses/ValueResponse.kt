package net.dungeonhub.hypixel.client.responses

import kotlin.time.Instant

sealed interface ValueResponse<out T> : ApiResponse<T> {
    val value: T
    val timestamp: Instant
    val origin: DataOrigin
}
