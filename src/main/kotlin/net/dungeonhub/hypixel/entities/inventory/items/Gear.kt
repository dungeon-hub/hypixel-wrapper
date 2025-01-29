package net.dungeonhub.hypixel.entities.inventory.items

import me.nullicorn.nedit.type.NBTCompound

//TODO check if those fields are used somewhere else
//TODO map reforge data
//TODO map enchantment data
/**
 * Gear is used as an abstract class for weapons, armor, fishing rods and equipment
 */
open class Gear(raw: NBTCompound) : SkyblockItem(raw), EnchantableItem {
    val reforge: String?
        get() = extraAttributes.getString("modifier")

    val hotPotatoes: Int
        get() = extraAttributes.getInt("hot_potato_count", 0)
}