package net.dungeonhub.hypixel.entities.inventory.items

interface FishingTool : SkyblockItemFactory {
    val expertiseKills: Int?
        get() = extraAttributes.getInt("expertise_kills", -1).takeIf { it != -1 }
}