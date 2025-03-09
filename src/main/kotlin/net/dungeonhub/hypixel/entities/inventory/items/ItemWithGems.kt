package net.dungeonhub.hypixel.entities.inventory.items

import net.dungeonhub.hypixel.entities.inventory.ItemGemsData
import net.dungeonhub.hypixel.entities.inventory.toGemsData

interface ItemWithGems : SkyblockItemFactory {
    val gems: ItemGemsData?
        get() = extraAttributes.getCompound("gems")?.toGemsData()
}