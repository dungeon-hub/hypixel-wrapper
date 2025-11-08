package net.dungeonhub.cache.database

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.bson.Document

object MongoCacheProvider {
    private const val DEFAULT_DATABASE_NAME = "hypixel-wrapper-cache"
    private const val DEFAULT_COLLECTION_PREFIX = "hypixel-wrapper"

    var connectionString: String? = System.getenv("HYPIXEL_API_CACHE_DATABASE_URI")
    var databaseName: String = System.getenv("HYPIXEL_API_CACHE_DATABASE_NAME") ?: DEFAULT_DATABASE_NAME
    var collectionPrefix: String = System.getenv("HYPIXEL_API_CACHE_DATABASE_PREFIX") ?: DEFAULT_COLLECTION_PREFIX

    val isConfigured: Boolean
        get() = !connectionString.isNullOrBlank()

    private val client: MongoClient by lazy {
        val uri = connectionString
        require(!uri.isNullOrBlank()) { "Mongo cache requires HYPIXEL_API_CACHE_DATABASE_URI to be set" }
        MongoClients.create(uri)
    }

    private val database: MongoDatabase by lazy {
        client.getDatabase(databaseName)
    }

    fun getCollection(resourceName: String): MongoCollection<Document> {
        val collectionName = buildString {
            val prefix = collectionPrefix.trim()
            if (prefix.isNotEmpty()) {
                append(prefix)
                append('_')
            }
            append(resourceName)
        }
        return database.getCollection(collectionName)
    }
}

