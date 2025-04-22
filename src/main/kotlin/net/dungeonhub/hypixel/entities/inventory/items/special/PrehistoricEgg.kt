package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

class PrehistoricEgg(raw: NBTCompound) : SkyblockItem(raw) {
    val blocksWalked: Int
        get() = extraAttributes.getInt("blocks_walked", 0)

    val rolled4000: Boolean
        get() = extraAttributes.getInt("rolled_4000", 0) == 1

    val rolled10000: Boolean
        get() = extraAttributes.getInt("rolled_10000", 0) == 1

    val rolled20000: Boolean
        get() = extraAttributes.getInt("rolled_20000", 0) == 1

    val rolled40000: Boolean
        get() = extraAttributes.getInt("rolled_40000", 0) == 1
}