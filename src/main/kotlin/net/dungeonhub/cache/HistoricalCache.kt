package net.dungeonhub.cache

import net.dungeonhub.cache.memory.CacheElement
import java.time.Instant

interface HistoricalCache<T, K> : Cache<T, K> {
    fun retrieveAt(key: K, before: Instant? = null, after: Instant? = null): T? {
        return retrieveElementAt(key, before, after)?.value
    }

    fun retrieveElementAt(key: K, before: Instant? = null, after: Instant? = null): CacheElement<T>?
}
