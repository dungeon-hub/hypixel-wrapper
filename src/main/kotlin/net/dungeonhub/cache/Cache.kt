package net.dungeonhub.cache

import net.dungeonhub.cache.memory.CacheElement

interface Cache<T, K> {
    fun retrieve(key: K): T? {
        return retrieveElement(key)?.value
    }

    fun retrieveElement(key: K): CacheElement<T>?

    fun retrieveAll(): List<T> {
        return retrieveAllElements().map { it.value }
    }

    fun retrieveAllElements(): List<CacheElement<T>>

    fun store(value: T)
}
