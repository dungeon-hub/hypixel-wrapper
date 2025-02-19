package net.dungeonhub.cache

import net.dungeonhub.cache.memory.CacheElement

interface Cache<T, K> {
    fun retrieve(key: K): T? = retrieveElement(key)?.value

    fun retrieveElement(key: K): CacheElement<T>?

    fun retrieveAll(): List<T> = retrieveAllElements().map { it.value }

    fun retrieveAllElements(): List<CacheElement<T>>

    fun store(value: T)

    fun invalidateEntry(key: K)
}
