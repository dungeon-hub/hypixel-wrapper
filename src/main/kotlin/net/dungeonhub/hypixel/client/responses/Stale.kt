package net.dungeonhub.hypixel.client.responses

import kotlin.time.Instant

data class Stale<T>(
    override val value: T,
    override val timestamp: Instant,
    override val origin: DataOrigin,
    val cause: Throwable? = null
) : ValueResponse<T> {
    override fun <R> map(transform: (T) -> R): ApiResponse<R> = Stale(
        value = transform(value),
        timestamp = timestamp,
        origin = origin,
        cause = cause
    )

    override fun asStale(): ApiResponse<T> = this
}
