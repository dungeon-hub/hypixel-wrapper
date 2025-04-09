package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.ItemStack
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem
import net.dungeonhub.hypixel.entities.inventory.readItemStacks

class BasketOfSeeds(raw: NBTCompound) : SkyblockItem(raw) {
    val content: List<ItemStack?>
        get() = extraAttributes.getByteArray("basket_of_seeds_data")?.readItemStacks() ?: emptyList()
}