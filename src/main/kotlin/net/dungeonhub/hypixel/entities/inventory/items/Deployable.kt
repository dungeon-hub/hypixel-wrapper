package net.dungeonhub.hypixel.entities.inventory.items

import me.nullicorn.nedit.type.NBTCompound

//TODO check fields
class Deployable(raw: NBTCompound) : SkyblockItem(raw), ManaDisintegratable, SkinAppliable, ItemWithAbility {
    val hasJalapeno: Boolean
        get() = extraAttributes.getInt("jalapeno_count", 0) > 0
}