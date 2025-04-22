package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.ItemStack
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem
import net.dungeonhub.hypixel.entities.inventory.items.parseItemList

class BuildersRuler(raw: NBTCompound) : SkyblockItem(raw) {
    val buildersRulerData: List<ItemStack>
        get() = extraAttributes.get("builder's_ruler_data")?.let {
            if (it is ByteArray) it.parseItemList() else emptyList()
        } ?: emptyList()
}