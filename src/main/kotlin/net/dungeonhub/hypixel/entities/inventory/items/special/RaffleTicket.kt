package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem
import java.util.*

class RaffleTicket(raw: NBTCompound) : SkyblockItem(raw) {
    val raffleUUID: UUID?
        get() = extraAttributes.getString("raffle_uuid")?.let { UUID.fromString(it) }
}