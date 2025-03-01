package net.dungeonhub.hypixel.entities.inventory.items

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.ItemStack

class BuildersRuler(raw: NBTCompound) : SkyblockItem(raw) {
    val buildersRulerData: List<ItemStack>
        get() = extraAttributes.get("builder's_ruler_data")?.let {
            if (it is ByteArray) it.parseItemList() else emptyList()
        } ?: emptyList()
}