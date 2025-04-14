package net.dungeonhub.hypixel.entities.inventory.items

import me.nullicorn.nedit.type.NBTCompound

//TODO maybe move SkinAppliable out of here -> create PowerOrb class
class Deployable(raw: NBTCompound) : SkyblockItem(raw), ManaDisintegratable, SkinAppliable, ItemWithAbility {
    val hasJalapeno: Boolean
        get() = extraAttributes.getInt("jalapeno_count", 0) > 0
}