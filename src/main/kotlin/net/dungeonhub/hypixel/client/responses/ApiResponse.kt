package net.dungeonhub.hypixel.client.responses

sealed interface ApiResponse<out T> {
    fun <R> map(transform: (T) -> R): ApiResponse<R>

    fun asStale(): ApiResponse<T>

    val valueOrNull: T?
        get() = (this as? ValueResponse<T>)?.value
}
