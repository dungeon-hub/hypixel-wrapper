package net.dungeonhub.hypixel.entities.inventory.items

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.ItemStack

class BuildersWand(raw: NBTCompound) : SkyblockItem(raw) {
    val buildersWandData: List<ItemStack>
        get() = extraAttributes.get("builder's_wand_data")?.let {
            if (it is ByteArray) it.parseItemList() else emptyList()
        } ?: emptyList()
}