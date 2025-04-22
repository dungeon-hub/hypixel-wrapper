package net.dungeonhub.cache.memory

import net.dungeonhub.cache.Cache
import java.time.Instant
import java.util.stream.Stream

class HashMapCache<T, K>(val keyFunction: (T) -> K) : Cache<T, K> {
    private val cache = HashMap<K, CacheElement<T>>()

    override fun retrieveElement(key: K): CacheElement<T>? {
        return cache[key]
    }

    override fun retrieveAllElements(): Stream<CacheElement<T>> {
        return cache.values.stream()
    }

    override fun store(value: T) {
        cache[keyFunction(value)] = CacheElement(timeAdded = Instant.now(), value = value)
    }

    override fun invalidateEntry(key: K) {
        cache.remove(key)
    }
}