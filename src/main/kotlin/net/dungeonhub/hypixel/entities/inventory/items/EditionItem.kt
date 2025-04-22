package net.dungeonhub.hypixel.entities.inventory.items

import java.time.Instant
import java.util.*

interface EditionItem : SkyblockItemFactory {
    /**
     * On some items the actual edition number is -1, in that case I assume that the memento shouldn't have an edition.
     * In such cases, the edition will be null.
     */
    val edition: Int?
        get() = extraAttributes.getInt("edition", -1).takeIf { it != -1 }

    val recipientName: String?
        get() = extraAttributes.getString("recipient_name")

    val recipientUuid: UUID?
        get() = extraAttributes.getString("recipient_id")?.let { UUID.fromString(it) }

    /**
     * On items that are gifted by an admin, this is their display name.
     */
    val sender: String?
        get() = extraAttributes.getString("sender_name")

    val dateObtained: Instant?
        get() = extraAttributes.getLong("date", -1).takeIf { it != -1L }?.let { Instant.ofEpochMilli(it) }
}