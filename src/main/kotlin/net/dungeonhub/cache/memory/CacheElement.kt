package net.dungeonhub.cache.memory

import java.time.Instant

class CacheElement<T>(val timeAdded: Instant, val value: T)