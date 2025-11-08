package net.dungeonhub.cache

import net.dungeonhub.cache.database.MongoCacheProvider

enum class CacheType {
    Memory,
    Disk,
    Database;

    val active: Boolean
        get() = when (this) {
            Memory -> true
            Disk -> true
            Database -> MongoCacheProvider.isConfigured
        }
}
