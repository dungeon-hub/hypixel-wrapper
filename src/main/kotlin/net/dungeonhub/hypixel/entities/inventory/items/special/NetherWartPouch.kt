package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.ItemStack
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem
import net.dungeonhub.hypixel.entities.inventory.readItemStacks

class NetherWartPouch(raw: NBTCompound) : SkyblockItem(raw) {
    val content: List<ItemStack?>
        get() = extraAttributes.getByteArray("nether_wart_pouch_data")?.readItemStacks()
            ?: extraAttributes.getByteArray("netherwart_pouch_data")?.readItemStacks()
            ?: emptyList()
}