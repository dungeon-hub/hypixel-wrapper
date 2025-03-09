package net.dungeonhub.hypixel.entities.inventory.items.id

import net.dungeonhub.hypixel.entities.inventory.items.PetAsItem
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

enum class PetItemId(override val apiName: String, override val itemClass: ((SkyblockItem) -> SkyblockItem)) : KnownSkyblockItemId {
    //TODO - Pet Items
    Pet("PET", { PetAsItem(it.raw) }),
}