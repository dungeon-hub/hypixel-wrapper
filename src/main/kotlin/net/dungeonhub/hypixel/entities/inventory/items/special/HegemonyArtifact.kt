package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Accessory

class HegemonyArtifact(raw: NBTCompound) : Accessory(raw) {
    val winningBid: Long //TODO extract into interface
        get() = extraAttributes.getLong("winning_bid", 0)
}