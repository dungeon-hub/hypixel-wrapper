package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.ItemStack
import net.dungeonhub.hypixel.entities.inventory.items.Accessory
import net.dungeonhub.hypixel.entities.inventory.items.parseItemList

class NewYearCakeBag(raw: NBTCompound) : Accessory(raw) {
    val newYearCakeBagData: List<ItemStack>
        get() = extraAttributes["new_year_cake_bag_data"]?.let {
            if (it is ByteArray) it.parseItemList() else emptyList()
        } ?: emptyList()
}