package net.dungeonhub.hypixel.entities.inventory.items.id

import net.dungeonhub.hypixel.entities.inventory.items.Gear
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

enum class ShearItemId(override val apiName: String, override val itemClass: ((SkyblockItem) -> SkyblockItem)) :
    KnownSkyblockItemId {
    EnchantedShears("ENCHANTED_SHEARS", { Gear(it.raw) }),
    MobysShears("MOBYS_SHEARS", { Gear(it.raw) }),
    Shears("SHEARS", { Gear(it.raw) }),
}