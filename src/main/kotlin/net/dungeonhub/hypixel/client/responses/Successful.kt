package net.dungeonhub.hypixel.client.responses

import kotlin.time.Clock
import kotlin.time.Instant

data class Successful<T>(
    override val value: T,
    override val origin: DataOrigin,
    override val timestamp: Instant = Clock.System.now()
) : ValueResponse<T> {
    override fun <R> map(transform: (T) -> R): ApiResponse<R> = Successful(
        value = transform(value),
        timestamp = timestamp,
        origin = origin
    )

    override fun asStale(): Stale<T> = Stale(value, timestamp, origin)
}
