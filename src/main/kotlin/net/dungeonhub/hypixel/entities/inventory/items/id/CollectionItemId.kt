package net.dungeonhub.hypixel.entities.inventory.items.id

import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

//TODO move other collection items here
enum class CollectionItemId(override val apiName: String, override val itemClass: ((SkyblockItem) -> SkyblockItem)) :
    KnownSkyblockItemId {
    Cobblestone("COBBLESTONE"),
    Diamond("DIAMOND"),
    EnchantedClownfish("ENCHANTED_CLOWNFISH"),
    Gunpowder("SULPHUR"),
    Leather("LEATHER"),
    Mycelium("MYCEL"),
    PrismarineCrystals("PRISMARINE_CRYSTALS"),
    PrismarineShard("PRISMARINE_SHARD"),
    Redstone("REDSTONE"),
    String("STRING"),
    Sulphur("SULPHUR_ORE");

    constructor(apiName: String) : this(apiName, { it })
}