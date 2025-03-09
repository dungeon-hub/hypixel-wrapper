package net.dungeonhub.hypixel.entities.inventory.items

import me.nullicorn.nedit.type.NBTCompound

//TODO check fields
//TODO should ItemWithAbility be moved somewhere else?
//TODO should this really contain tools as well? would be easy to abstract away
/**
 * Gear is used as an abstract class for weapons, armor, tools, fishing rods and equipment
 */
open class Gear(raw: NBTCompound) : SkyblockItem(raw), EnchantableItem, ReforgeableItem, ItemWithAbility, ItemWithGems {
    //TODO map to attribute type
    val attributes: Map<String, Int>
        get() = extraAttributes.getCompound("attributes")?.let {
            it.mapValues { attribute -> attribute.value as Int }
        } ?: emptyMap()

    val runes: Map<String, Int>
        get() = extraAttributes.getCompound("runes")?.let {
            it.mapValues { rune -> rune.value as Int }
        } ?: emptyMap()

    val dungeonItem: Boolean
        get() = extraAttributes.getByte("dungeon_item", 0) == 1.toByte()

    val starAmount: Int
        get() = extraAttributes.getInt("upgrade_level", 0)

    //TODO map this in a new class only?
    // This is the year in which the event item was obtained, for example for the Great Spook items
    val year: Int?
        get() = extraAttributes.getInt("year", 0).takeIf { it != 0 }
}