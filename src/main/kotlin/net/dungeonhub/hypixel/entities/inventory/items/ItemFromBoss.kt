package net.dungeonhub.hypixel.entities.inventory.items

import java.util.*

interface ItemFromBoss : SkyblockItemFactory {
    val bossId: UUID?
        get() = extraAttributes.getString("bossId")?.let { UUID.fromString(it) }

    val spawnedFor: UUID?
        get() = extraAttributes.getString("spawnedFor")?.let { UUID.fromString(it) }
}