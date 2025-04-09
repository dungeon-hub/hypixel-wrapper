package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import java.time.Instant

class DarkCacaoTruffle(raw: NBTCompound) : TimeBagItem(raw) {
    val lastForceEvolvedTime: Instant?
        get() = extraAttributes.getLong("lastForceEvolvedTime", -1).takeIf { it != -1L }
            ?.let { Instant.ofEpochMilli(it) }
}