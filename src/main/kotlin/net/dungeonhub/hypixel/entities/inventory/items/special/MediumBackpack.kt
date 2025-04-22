package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.BackpackContent
import net.dungeonhub.hypixel.entities.inventory.toBackpackContentData

class MediumBackpack(raw: NBTCompound) : Backpack(raw) {
    override val backpackData: BackpackContent?
        get() = extraAttributes.getByteArray("medium_backpack_data")?.toBackpackContentData()
}