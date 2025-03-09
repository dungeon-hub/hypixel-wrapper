## Hypixel Wrapper

This library for Kotlin/Java allows you to interact with the Hypixel API, while exposing fields as they appear in the
API.

## Before usage

Before you use the library, you should set some settings. This is done by modifying some public variables or setting
environment variables:

| Field                                    | Description                                                        | Possible values                      | Environment Variable   | Package (for Import)              |
|------------------------------------------|--------------------------------------------------------------------|--------------------------------------|------------------------|-----------------------------------|
| `HypixelConnection.apiKey`               | Your Hypixel API Key                                               | Any valid UUID (API-Key) as a String | HYPIXEL_API_KEY        | net.dungeonhub.hypixel.connection |
| `CacheApiClientProvider.cacheTypeString` | The type of caching you want to use by default (Memory by default) | Memory, Disk, Database               | HYPIXEL_API_CACHE_TYPE | net.dungeonhub.hypixel.provider   |
| `DiskHistoryUUIDCache.cacheDirectory`    | The directory where the disk cache should be stored                | Any valid folder path                |                        | net.dungeonhub.cache.disk         |

## How to use the Library

To connect to the Hypixel API, you can simply instantiate the class `HypixelApiConnection`. After creation of this
class, you have the chance to set the Strategy by calling the `withStrategy()` method, or to change the cache expiration
by calling the `withCacheExpiration()` method.

You can then use your instance of `HypixelApiConnection` to send requests, for example getting all Skyblock profiles of
a user (`HypixelApiConnection#getSkyblockProfiles(UUID)`).

If you need to access the Mojang API for converting between usernames and their UUID, you can do so by using the
`MojangConnection` object.

## Samples

There are some samples available that show how you can use the library.
If you are interested in that, check out the repository `TODO`. 
