package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.BackpackContent
import net.dungeonhub.hypixel.entities.inventory.toBackpackContentData

class LargeBackpack(raw: NBTCompound) : Backpack(raw) {
    override val backpackData: BackpackContent?
        get() = extraAttributes.getByteArray("large_backpack_data")?.toBackpackContentData()
}