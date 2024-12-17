package net.dungeonhub.cache.memory

import net.dungeonhub.cache.Cache
import java.time.Instant

class HashMapCache<T, K>(val keyFunction: (T) -> K) : Cache<T, K> {
    private val cache = HashMap<K, CacheElement<T>>()

    override fun retrieveElement(key: K): CacheElement<T>? {
        return cache[key]
    }

    override fun retrieveAllElements(): List<CacheElement<T>> {
        return cache.values.toList()
    }

    override fun store(value: T) {
        cache[keyFunction(value)] = CacheElement(timeAdded = Instant.now(), value = value)
    }
}