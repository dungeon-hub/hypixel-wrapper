package net.dungeonhub.hypixel.entities.inventory.items

interface ItemWithCombatBooks : SkyblockItemFactory {
    val hasArtOfWar: Boolean
        get() = extraAttributes.getInt("art_of_war_count", 0) > 0

    val statsBook: Int?
        get() = extraAttributes.getInt("stats_book", -1).takeIf { it != -1 }
}