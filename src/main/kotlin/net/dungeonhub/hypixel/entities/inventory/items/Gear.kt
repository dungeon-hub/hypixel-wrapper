package net.dungeonhub.hypixel.entities.inventory.items

import me.nullicorn.nedit.type.NBTCompound

//TODO check fields
//TODO map reforge data
//TODO map enchantment data
//TODO should ItemWithAbility be moved somewhere else?
//TODO should this really contain tools as well? would be easy to abstract away
/**
 * Gear is used as an abstract class for weapons, armor, tools, fishing rods and equipment
 */
open class Gear(raw: NBTCompound) : SkyblockItem(raw), EnchantableItem, ReforgeableItem, ItemWithAbility {
    //TODO map to attribute type
    val attributes: Map<String, Int>
        get() = extraAttributes.getCompound("attributes")?.let {
            it.mapValues { attribute -> attribute.value as Int }
        } ?: emptyMap()
}