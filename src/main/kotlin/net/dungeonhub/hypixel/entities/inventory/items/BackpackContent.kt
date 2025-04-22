package net.dungeonhub.hypixel.entities.inventory.items

import net.dungeonhub.hypixel.entities.inventory.ItemStack
import net.dungeonhub.hypixel.entities.inventory.readItemStacks

class BackpackContent(val data: ByteArray) {
    val items: List<ItemStack?>
        get() = data.readItemStacks()
}