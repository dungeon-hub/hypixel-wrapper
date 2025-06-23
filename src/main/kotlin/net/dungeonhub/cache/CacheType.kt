package net.dungeonhub.cache

enum class CacheType {
    Memory,
    Disk,
    Database,
    Redis;

    val active: Boolean
        get() = TODO("implement")
}