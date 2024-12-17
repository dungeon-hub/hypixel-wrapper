package net.dungeonhub.exception

open class FailedToLoadException : RuntimeException {
    constructor(throwable: Throwable?) : super(throwable)

    constructor(message: String?) : super(message)
}