package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Accessory
import net.dungeonhub.hypixel.entities.inventory.items.KnownSkyblockItemId
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItemId

class PersonalDeletor(raw: NBTCompound) : Accessory(raw) {
    val active: Boolean
        get() = extraAttributes.getByte("PERSONAL_DELETOR_ACTIVE", 0) == 1.toByte()

    val deletorSlots: List<SkyblockItemId>
        get() = extraAttributes.filter { it.key.startsWith("personal_deletor_") }.map {
            KnownSkyblockItemId.fromApiName(it.value.toString())
        }
}