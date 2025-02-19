package net.dungeonhub.cache.memory

import java.time.Instant

data class CacheElement<T>(val timeAdded: Instant, val value: T)