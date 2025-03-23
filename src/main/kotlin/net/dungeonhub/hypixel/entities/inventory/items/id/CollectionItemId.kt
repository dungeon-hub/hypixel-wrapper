package net.dungeonhub.hypixel.entities.inventory.items.id

import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

//TODO move other collection items here
enum class CollectionItemId(override val apiName: String, override val itemClass: ((SkyblockItem) -> SkyblockItem)) :
    KnownSkyblockItemId {
    BlazeRod("BLAZE_ROD"),
    BrownMushroom("BROWN_MUSHROOM"),
    Carrots("CARROT"),
    ClayBall("CLAY_BALL"),
    Coal("COAL"),
    Cobblestone("COBBLESTONE"),
    CocoaPlant("COCOA"),
    Diamond("DIAMOND"),
    Emerald("EMERALD"),
    EnchantedClownfish("ENCHANTED_CLOWNFISH"),
    EndStone("ENDER_STONE"),
    GhastTear("GHAST_TEAR"),
    Glacite("GLACITE"),
    GlowstoneDust("GLOWSTONE_DUST"),
    Gunpowder("SULPHUR"),
    HardStone("HARD_STONE"),
    Hemovibe("HEMOVIBE"),
    Ice("ICE"),
    IronIngot("IRON_INGOT"),
    Leather("LEATHER"),
    LilyPad("WATER_LILY"),
    MagmaCream("MAGMA_CREAM"),
    Magmafish("MAGMA_FISH"),
    Mithril("MITHRIL_ORE"),
    Mycelium("MYCEL"),
    NetherQuartz("QUARTZ"),
    NetherWart("NETHER_STALK"),
    Potatoes("POTATO"),
    PrismarineCrystals("PRISMARINE_CRYSTALS"),
    PrismarineShard("PRISMARINE_SHARD"),
    RedMushroom("RED_MUSHROOM"),
    RedSand("SAND:1"),
    Redstone("REDSTONE"),
    Seeds("SEEDS"),
    Slimeball("SLIME_BALL"),
    String("STRING"),
    SugarCane("SUGAR_CANE"),
    Sulphur("SULPHUR_ORE"),
    Tungsten("TUNGSTEN"),
    Umber("UMBER"),
    Wheat("WHEAT");

    constructor(apiName: String) : this(apiName, { it })
}