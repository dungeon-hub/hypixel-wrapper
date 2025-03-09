package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Accessory
import net.dungeonhub.hypixel.entities.inventory.items.id.KnownSkyblockItemId
import net.dungeonhub.hypixel.entities.inventory.items.id.SkyblockItemId

class PersonalCompactor(raw: NBTCompound) : Accessory(raw) {
    // Pretty funny that this isn't named PERSONAL_COMPACTOR_ACTIVE - Hypixel is pretty lazy lol
    val active: Boolean
        get() = extraAttributes.getByte("PERSONAL_DELETOR_ACTIVE", 0) == 1.toByte()

    val compactSlots: List<SkyblockItemId>
        get() = extraAttributes.filter { it.key.startsWith("personal_compact_") }.map {
            KnownSkyblockItemId.fromApiName(it.value.toString())
        }
}