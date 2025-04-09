package net.dungeonhub.hypixel.entities.inventory.items

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.skyblock.dungeon.DungeonType
import net.dungeonhub.hypixel.entities.skyblock.dungeon.KnownDungeonType

//TODO check fields
//TODO should ItemWithAbility be moved somewhere else?
//TODO should this really contain tools as well? would be easy to abstract away
/**
 * Gear is used as an abstract class for weapons, armor, tools, fishing rods and equipment
 */
open class Gear(raw: NBTCompound) : SkyblockItem(raw), EnchantableItem, ReforgeableItem, ItemWithAbility, ItemWithGems,
    ItemWithAttributes, ItemWithRune {
    val dungeonItem: Boolean
        get() = extraAttributes.getByte("dungeon_item", 0) == 1.toByte()

    val starAmount: Int
        get() = extraAttributes.getInt("upgrade_level", 0)

    val dungeonSkillRequirement: Pair<DungeonType, Int>?
        get() = extraAttributes.getString("dungeon_skill_req")?.split(":")?.let {
            KnownDungeonType.fromApiName(it.first().lowercase()) to Integer.valueOf(it.last())
        }

    val baseStatBoost: Int?
        get() = extraAttributes.getInt("baseStatBoostPercentage", -1).takeIf { it != -1 }

    val itemTier: Int?
        get() = extraAttributes.getInt("item_tier", -1).takeIf { it != -1 }

    /**
     * This is only present on some dungeon items
     */
    val itemDurability: Int?
        get() = extraAttributes.getInt("item_durability", -1).takeIf { it != -1 }

    //TODO map this in a new class only?
    // This is the year in which the event item was obtained, for example for the Great Spook items
    val year: Int?
        get() = extraAttributes.getInt("year", 0).takeIf { it != 0 }
}