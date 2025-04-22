package net.dungeonhub.cache

import net.dungeonhub.cache.memory.CacheElement
import java.util.stream.Stream

interface Cache<T, K> {
    fun retrieve(key: K): T? {
        return retrieveElement(key)?.value
    }

    fun retrieveElement(key: K): CacheElement<T>?

    fun retrieveAll(): Stream<T> {
        return retrieveAllElements().map { it.value }
    }

    fun retrieveAllElements(): Stream<CacheElement<T>>

    fun store(value: T)

    fun invalidateEntry(key: K)
}

fun <T> Stream<T?>.filterNotNull(): Stream<T> {
    return this.filter { it != null }.map {
        @Suppress("UNCHECKED_CAST") // It is indeed checked, see the previous statement
        it as T
    }
}
