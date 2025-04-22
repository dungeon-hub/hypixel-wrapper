package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

class Minion(raw: NBTCompound) : SkyblockItem(raw) {
    val resourcesGenerated: Int
        get() = extraAttributes.getInt("resources_generated", 0)

    val mithrilInfused: Boolean
        get() = extraAttributes.getInt("mithril_infusion", 0) == 1

    val freeWill: Boolean
        get() = extraAttributes.getInt("free_will", 0) == 1

    val minionTier: Int?
        get() = extraAttributes.getInt("generator_tier", -1).takeIf { it != -1 }

    /**
     * This describes how many actions the minion has done an action, which resets on world change, when the minion is placed or updated with new data in some other way.
     */
    val totalGenerations: Int?
        get() = extraAttributes.getInt("total_generations", -1).takeIf { it != -1 }
}