package net.dungeonhub.hypixel.client.responses

data class Unavailable(
    val cause: Throwable? = null
) : ApiResponse<Nothing> {
    override fun <R> map(transform: (Nothing) -> R): ApiResponse<R> = this

    override fun asStale() = this
}
