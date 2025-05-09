package net.dungeonhub.hypixel.entities.inventory.items.id

import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem
import net.dungeonhub.hypixel.entities.inventory.items.special.Sack

enum class ItemSackId(override val apiName: String, override val itemClass: ((SkyblockItem) -> SkyblockItem)) :
    KnownSkyblockItemId {
    BronzeTrophyFishingSack("BRONZE_TROPHY_FISHING_SACK", { Sack(it.raw) }),
    CrystalHollowsSack("CRYSTAL_HOLLOWS_SACK", { Sack(it.raw) }),
    DungeonSack("LARGE_DUNGEON_SACK", { Sack(it.raw) }),
    DwarvenSack("DWARVEN_MINES_SACK", { Sack(it.raw) }),
    EventsSack("LARGE_EVENTS_SACK", { Sack(it.raw) }),
    FlowerSack("FLOWER_SACK", { Sack(it.raw) }),
    GardenSack("GARDEN_SACK", { Sack(it.raw) }),
    LargeAgronomySack("LARGE_AGRONOMY_SACK", { Sack(it.raw) }),
    LargeCombatSack("LARGE_COMBAT_SACK", { Sack(it.raw) }),
    LargeDragonSack("LARGE_DRAGON_SACK", { Sack(it.raw) }),
    LargeEnchantedAgronomySack("LARGE_ENCHANTED_AGRONOMY_SACK", { Sack(it.raw) }),
    LargeEnchantedCombatSack("LARGE_ENCHANTED_COMBAT_SACK", { Sack(it.raw) }),
    LargeEnchantedFishingSack("LARGE_ENCHANTED_FISHING_SACK", { Sack(it.raw) }),
    LargeEnchantedForagingSack("LARGE_ENCHANTED_FORAGING_SACK", { Sack(it.raw) }),
    LargeEnchantedHusbandrySack("LARGE_ENCHANTED_HUSBANDRY_SACK", { Sack(it.raw) }),
    LargeEnchantedMiningSack("LARGE_ENCHANTED_MINING_SACK", { Sack(it.raw) }),
    LargeFishingSack("LARGE_FISHING_SACK", { Sack(it.raw) }),
    LargeForagingSack("LARGE_FORAGING_SACK", { Sack(it.raw) }),
    LargeGemstoneSack("LARGE_GEMSTONE_SACK", { Sack(it.raw) }),
    LargeHusbandrySack("LARGE_HUSBANDRY_SACK", { Sack(it.raw) }),
    LargeLavaFishingSack("LARGE_LAVA_FISHING_SACK", { Sack(it.raw) }),
    LargeMiningSack("LARGE_MINING_SACK", { Sack(it.raw) }),
    LargeNetherSack("LARGE_NETHER_SACK", { Sack(it.raw) }),
    LargeSlayerSack("LARGE_SLAYER_SACK", { Sack(it.raw) }),
    MediumAgronomySack("MEDIUM_AGRONOMY_SACK", { Sack(it.raw) }),
    MediumCombatSack("MEDIUM_COMBAT_SACK", { Sack(it.raw) }),
    MediumDragonSack("MEDIUM_DRAGON_SACK", { Sack(it.raw) }),
    MediumFishingSack("MEDIUM_FISHING_SACK", { Sack(it.raw) }),
    MediumForagingSack("MEDIUM_FORAGING_SACK", { Sack(it.raw) }),
    MediumGemstoneSack("MEDIUM_GEMSTONE_SACK", { Sack(it.raw) }),
    MediumHusbandrySack("MEDIUM_HUSBANDRY_SACK", { Sack(it.raw) }),
    MediumLavaFishingSack("MEDIUM_LAVA_FISHING_SACK", { Sack(it.raw) }),
    MediumMiningSack("MEDIUM_MINING_SACK", { Sack(it.raw) }),
    MediumNetherSack("MEDIUM_NETHER_SACK", { Sack(it.raw) }),
    MediumSlayerSack("MEDIUM_SLAYER_SACK", { Sack(it.raw) }),
    RuneSack("RUNE_SACK", { Sack(it.raw) }),
    SilverTrophyFishingSack("SILVER_TROPHY_FISHING_SACK", { Sack(it.raw) }),
    SmallAgronomySack("SMALL_AGRONOMY_SACK", { Sack(it.raw) }),
    SmallCombatSack("SMALL_COMBAT_SACK", { Sack(it.raw) }),
    SmallDragonSack("SMALL_DRAGON_SACK", { Sack(it.raw) }),
    SmallFishingSack("SMALL_FISHING_SACK", { Sack(it.raw) }),
    SmallForagingSack("SMALL_FORAGING_SACK", { Sack(it.raw) }),
    SmallGemstoneSack("SMALL_GEMSTONE_SACK", { Sack(it.raw) }),
    SmallHusbandrySack("SMALL_HUSBANDRY_SACK", { Sack(it.raw) }),
    SmallLavaFishingSack("SMALL_LAVA_FISHING_SACK", { Sack(it.raw) }),
    SmallMiningSack("SMALL_MINING_SACK", { Sack(it.raw) }),
    SmallNetherSack("SMALL_NETHER_SACK", { Sack(it.raw) }),
    SmallSlayerSack("SMALL_SLAYER_SACK", { Sack(it.raw) }),
    SpookySack("LARGE_CANDY_SACK", { Sack(it.raw) }),
    WinterSack("LARGE_WINTER_SACK", { Sack(it.raw) });
}