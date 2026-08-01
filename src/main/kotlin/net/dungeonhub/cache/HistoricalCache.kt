package net.dungeonhub.cache

import net.dungeonhub.cache.memory.CacheElement
import java.time.Instant

interface HistoricalCache<T, K> : Cache<T, K> {
    /**
     * Retrieves the value selected by [retrieveElementAt] for the given inclusive time bounds.
     */
    fun retrieveAt(key: K, before: Instant? = null, after: Instant? = null): T? {
        return retrieveElementAt(key, before, after)?.value
    }

    /**
     * Retrieves a cached element whose timestamp is at or before [before] and at or after [after].
     * Both bounds are inclusive. When only [after] is provided, the earliest matching element is
     * returned; when [before] is provided (with or without [after]), the latest matching element is
     * returned. With neither bound, the latest element is returned.
     *
     * @return the selected element, or `null` when no element matches or [before] is earlier than [after]
     */
    fun retrieveElementAt(key: K, before: Instant? = null, after: Instant? = null): CacheElement<T>?
}
