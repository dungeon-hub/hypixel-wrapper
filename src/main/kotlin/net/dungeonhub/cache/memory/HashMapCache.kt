package net.dungeonhub.cache.memory

import net.dungeonhub.cache.Cache
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap
import java.util.stream.Stream

class HashMapCache<T, K>(val keyFunction: (T) -> K) : Cache<T, K> {
    private val cache: MutableMap<K, CacheElement<T>> = ConcurrentHashMap()

    override fun retrieveElement(key: K): CacheElement<T>? = cache[key]

    override fun retrieveAllElements(): Stream<CacheElement<T>> = cache.values.stream()

    override fun store(value: T, waitForInsertion: Boolean) {
        cache[keyFunction(value)] = CacheElement(timeAdded = Instant.now(), value = value)
    }

    override fun invalidateEntry(key: K) {
        cache.remove(key)
    }
}