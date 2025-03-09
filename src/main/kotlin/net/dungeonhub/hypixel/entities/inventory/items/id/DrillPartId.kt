package net.dungeonhub.hypixel.entities.inventory.items.id

import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

enum class DrillPartId(override val apiName: String, override val itemClass: ((SkyblockItem) -> SkyblockItem)) :
    KnownSkyblockItemId {
    AmberPolishedDrillEngine("AMBER_POLISHED_DRILL_ENGINE"),
    Biofuel("BIOFUEL"),
    BlueCheeseGoblinOmelette("GOBLIN_OMELETTE_BLUE_CHEESE"),
    GemstoneFuelTank("GEMSTONE_FUEL_TANK"),
    GoblinOmelette("GOBLIN_OMELETTE"),
    MithrilInfusedFuelTank("MITHRIL_FUEL_TANK"),
    MithrilPlatedDrillEngine("MITHRIL_DRILL_ENGINE"),
    OilBarrel("OIL_BARREL"),
    PerfectlyCutFuelTank("PERFECTLY_CUT_FUEL_TANK"),
    PestoGoblinOmelette("GOBLIN_OMELETTE_PESTO"),
    RubyPolishedDrillEngine("RUBY_POLISHED_DRILL_ENGINE"),
    SapphirePolishedDrillEngine("SAPPHIRE_POLISHED_DRILL_ENGINE"),
    SpicyGoblinOmelette("GOBLIN_OMELETTE_SPICY"),
    StarfallSeasoning("STARFALL_SEASONING"),
    SunnySideGoblinOmelette("GOBLIN_OMELETTE_SUNNY_SIDE"),
    TitaniumInfusedFuelTank("TITANIUM_FUEL_TANK"),
    TitaniumPlatedDrillEngine("TITANIUM_DRILL_ENGINE");

    constructor(apiName: String) : this(apiName, { it })
}