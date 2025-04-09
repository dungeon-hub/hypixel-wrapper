package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.BackpackContent
import net.dungeonhub.hypixel.entities.inventory.toBackpackContentData

class SmallBackpack(raw: NBTCompound) : Backpack(raw) {
    override val backpackData: BackpackContent?
        get() = extraAttributes.getByteArray("small_backpack_data")?.toBackpackContentData()
}