package net.dungeonhub.hypixel.entities.inventory.items

import me.nullicorn.nedit.type.NBTCompound

open class Deployable(raw: NBTCompound) : SkyblockItem(raw), ManaDisintegratable, ItemWithAbility {
    val hasJalapeno: Boolean
        get() = extraAttributes.getInt("jalapeno_count", 0) > 0
}