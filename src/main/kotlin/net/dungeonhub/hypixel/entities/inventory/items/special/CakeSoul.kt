package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem
import java.time.Instant

class CakeSoul(raw: NBTCompound) : SkyblockItem(raw) {
    val capturedPlayer: String?
        get() = extraAttributes.getString("captured_player")

    val capturedAt: Instant?
        get() = extraAttributes.getLong("captured_date", -1).takeIf { it != -1L }?.let { Instant.ofEpochMilli(it) }

    val capturedBy: String?
        get() = extraAttributes.getString("initiator_player")

    val cakeOwner: String?
        get() = extraAttributes.getString("cake_owner")

    val cakeSoulType: Int? //TODO map to enum
        get() = extraAttributes.getInt("soul_durability", -1).takeIf { it != -1 }
}