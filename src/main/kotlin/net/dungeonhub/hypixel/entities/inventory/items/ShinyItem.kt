package net.dungeonhub.hypixel.entities.inventory.items

interface ShinyItem : SkyblockItemFactory {
    val isShiny: Boolean
        get() = extraAttributes.getInt("is_shiny", 0) > 0
}