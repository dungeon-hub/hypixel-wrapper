package net.dungeonhub.hypixel.entities.inventory.items.id

import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

enum class BrewingIngredientId(override val apiName: String, override val itemClass: ((SkyblockItem) -> SkyblockItem)) :
    KnownSkyblockItemId {
    EnchantedCake("ENCHANTED_CAKE"),
    EnchantedRedstoneLamp("ENCHANTED_REDSTONE_LAMP"),
    FoulFlesh("FOUL_FLESH");

    constructor(apiName: String) : this(apiName, { it })
}