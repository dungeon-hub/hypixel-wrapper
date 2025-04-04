package net.dungeonhub.hypixel.entities.inventory.items.id

import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem
import net.dungeonhub.hypixel.entities.inventory.items.special.DrillPart

enum class DrillPartId(override val apiName: String, override val itemClass: ((SkyblockItem) -> SkyblockItem)) :
    KnownSkyblockItemId {
    AmberPolishedDrillEngine("AMBER_POLISHED_DRILL_ENGINE", { DrillPart(it.raw) }),
    BlueCheeseGoblinOmelette("GOBLIN_OMELETTE_BLUE_CHEESE", { DrillPart(it.raw) }),
    GemstoneFuelTank("GEMSTONE_FUEL_TANK", { DrillPart(it.raw) }),
    GoblinOmelette("GOBLIN_OMELETTE", { DrillPart(it.raw) }),
    MithrilInfusedFuelTank("MITHRIL_FUEL_TANK", { DrillPart(it.raw) }),
    MithrilPlatedDrillEngine("MITHRIL_DRILL_ENGINE", { DrillPart(it.raw) }),
    PerfectlyCutFuelTank("PERFECTLY_CUT_FUEL_TANK", { DrillPart(it.raw) }),
    PestoGoblinOmelette("GOBLIN_OMELETTE_PESTO", { DrillPart(it.raw) }),
    RubyPolishedDrillEngine("RUBY_POLISHED_DRILL_ENGINE", { DrillPart(it.raw) }),
    SapphirePolishedDrillEngine("SAPPHIRE_POLISHED_DRILL_ENGINE", { DrillPart(it.raw) }),
    SpicyGoblinOmelette("GOBLIN_OMELETTE_SPICY", { DrillPart(it.raw) }),
    StarfallSeasoning("STARFALL_SEASONING", { DrillPart(it.raw) }),
    SunnySideGoblinOmelette("GOBLIN_OMELETTE_SUNNY_SIDE", { DrillPart(it.raw) }),
    TitaniumInfusedFuelTank("TITANIUM_FUEL_TANK", { DrillPart(it.raw) }),
    TitaniumPlatedDrillEngine("TITANIUM_DRILL_ENGINE", { DrillPart(it.raw) });

    constructor(apiName: String) : this(apiName, { it })
}