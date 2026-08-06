# Hypixel Wrapper

[![Maven Central](https://img.shields.io/maven-central/v/net.dungeon-hub/hypixel-wrapper?label=Maven%20Central)](https://central.sonatype.com/artifact/net.dungeon-hub/hypixel-wrapper)

A Kotlin/JVM wrapper for the [Hypixel API](https://api.hypixel.net/) with typed models for player, guild, session,
SkyBlock profile, bingo, inventory, dungeon, pet, slayer, museum, and related data. The library includes memory, disk,
and MongoDB caches and can return stale cached data when the API is unavailable.

## Requirements

- Java 25 or newer
- A Hypixel API key for authenticated endpoints

## Installation

Hypixel Wrapper is published to Maven Central as `net.dungeon-hub:hypixel-wrapper`. The badge above always shows the
most recent published version; the examples below use dynamic version selectors so they resolve that release
automatically. Pin the version shown in the badge when reproducible builds are important.

### Gradle (Kotlin DSL)

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("net.dungeon-hub:hypixel-wrapper:[version]")
}
```

### Maven

```xml
<dependency>
    <groupId>net.dungeon-hub</groupId>
    <artifactId>hypixel-wrapper</artifactId>
    <version>[version]</version>
</dependency>
```

## Configuration

Set the API key before creating or using a connection. The recommended approach is the `HYPIXEL_API_KEY` environment
variable:

```bash
export HYPIXEL_API_KEY="your-api-key"
```

It can also be assigned in code:

```kotlin
import net.dungeonhub.hypixel.connection.HypixelConnection

HypixelConnection.apiKey = "your-api-key"
```

The following settings are available. Configure them before the first client is created because the default cache
client is initialized lazily.

| Setting | Environment variable | Default | Description |
| --- | --- | --- | --- |
| `HypixelConnection.apiKey` | `HYPIXEL_API_KEY` | None | Hypixel API key used by authenticated requests. |
| `CacheApiClientProvider.cacheTypeString` | `HYPIXEL_API_CACHE_TYPE` | `Memory` | Cache backend: `Memory`, `Disk`, or `Database` (case-sensitive). |
| `DiskHistoryCache.cacheDirectory` | None | `~/dungeon-hub/hypixel-wrapper-cache` | Root directory for current and historical disk-cache entries. |
| `MongoCacheProvider.connectionString` | `HYPIXEL_API_CACHE_DATABASE_URI` | None | MongoDB connection string. Required when using `Database`. |
| `MongoCacheProvider.databaseName` | `HYPIXEL_API_CACHE_DATABASE_NAME` | `hypixel-wrapper-cache` | MongoDB database name. |
| `MongoCacheProvider.collectionPrefix` | `HYPIXEL_API_CACHE_DATABASE_PREFIX` | `hypixel-wrapper` | Prefix added to MongoDB collection names. |

If `Database` is selected without a connection string, the client safely falls back to the in-memory cache.

## Usage

`HypixelApiConnection` uses `CacheWithRestFallback` by default. It reads fresh cached data first, fetches and stores REST
data on a cache miss, and can return stale cached data if the REST request fails.

```kotlin
import net.dungeonhub.hypixel.client.responses.Successful
import net.dungeonhub.hypixel.client.responses.Stale
import net.dungeonhub.hypixel.client.responses.Unavailable
import net.dungeonhub.hypixel.connection.HypixelApiConnection
import java.time.Duration
import java.util.UUID

val playerId = UUID.fromString("00000000-0000-0000-0000-000000000000")
val hypixel = HypixelApiConnection()
    .withCacheExpiration(Duration.ofMinutes(10))
    .withStaleCache()

when (val response = hypixel.getSkyblockProfiles(playerId)) {
    is Successful -> println("Loaded ${response.value.profiles.size} profiles from ${response.origin}")
    is Stale -> println("Using cached profiles from ${response.timestamp}")
    is Unavailable -> println("Profiles unavailable: ${response.cause?.message}")
}
```

For concise handling when the response metadata is not needed, use `valueOrNull`:

```kotlin
val player = hypixel.getPlayerData(playerId).valueOrNull
val discord = hypixel.getHypixelLinkedDiscord(playerId).valueOrNull
```

### Client strategies

Select a strategy in the constructor or with `withStrategy`:

```kotlin
import net.dungeonhub.hypixel.connection.HypixelApiConnection
import net.dungeonhub.strategy.ApiClientStrategy

val restOnly = HypixelApiConnection(ApiClientStrategy.Rest)
val diskOrRest = HypixelApiConnection().withStrategy(ApiClientStrategy.CacheWithRestFallback)
```

| Strategy | Behavior |
| --- | --- |
| `Rest` | Reads directly from the Hypixel REST API. |
| `Cache` | Reads only from the configured cache. |
| `CachingRest` | Reads from REST and stores successful responses in the cache. |
| `CacheWithRestFallback` | Reads from cache, then uses caching REST as a fallback. This is the default. |

For example, you can look up a player's Hypixel data, find their guild, inspect their SkyBlock profiles, or check their
online status. Helpers are also available for common tasks such as finding a linked Discord account and building a
profile stats overview.

### Mojang profile lookup

`MojangConnection` resolves names and UUIDs through the Minecraft Services API and keeps results in memory for three
hours:

```kotlin
import net.dungeonhub.mojang.connection.MojangConnection

val uuid = MojangConnection.getUUIDByName("PlayerName")
val name = MojangConnection.getNameByUUID(uuid)
```

## Local development

The Gradle wrapper downloads the configured toolchain when necessary. Build the library and run its tests with:

```bash
./gradlew build
```

To test changes from another project, publish the library to your local Maven repository:

```bash
./gradlew publishToMavenLocal
```

In the consuming project's Gradle configuration, add `mavenLocal()` before Maven Central so the locally published
artifact is considered first:

```kotlin
repositories {
    mavenLocal()
    mavenCentral()
}
```

Use `net.dungeon-hub:hypixel-wrapper` with the version declared in this repository's `build.gradle.kts`. Run
`publishToMavenLocal` again after making changes to replace the local artifact before retesting the consuming project.
