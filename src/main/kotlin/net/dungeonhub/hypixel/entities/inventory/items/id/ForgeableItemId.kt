package net.dungeonhub.hypixel.entities.inventory.items.id

import net.dungeonhub.hypixel.entities.inventory.items.Gear
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

enum class ForgeableItemId(override val apiName: String, override val itemClass: ((SkyblockItem) -> SkyblockItem)) :
    KnownSkyblockItemId {
    BejeweledHandle("BEJEWELED_HANDLE"),
    Chisel("CHISEL", { Gear(it.raw) }),
    DivansDrill("DIVAN_DRILL", { Gear(it.raw) }),
    DivansPowderCoating("DIVAN_POWDER_COATING"),
    DrillMotor("DRILL_ENGINE"),
    FuelCanister("FUEL_TANK"),
    GemstoneChamber("GEMSTONE_CHAMBER"),
    GemstoneDrillLT522("GEMSTONE_DRILL_2", { Gear(it.raw) }),
    GemstoneMixture("GEMSTONE_MIXTURE"),
    GlaciteAmalgamation("GLACITE_AMALGAMATION"),
    GlacitePlatedChisel("GLACITE_CHISEL", { Gear(it.raw) }),
    GoldenPlate("GOLDEN_PLATE"),
    JasperDrillX("GEMSTONE_DRILL_4", { Gear(it.raw) }),
    MithrilDrillSXR226("MITHRIL_DRILL_1", { Gear(it.raw) }),
    MithrilDrillSXR326("MITHRIL_DRILL_2", { Gear(it.raw) }),
    MithrilPlate("MITHRIL_PLATE"),
    PerfectChisel("PERFECT_CHISEL", { Gear(it.raw) }),
    PerfectPlate("PERFECT_PLATE"),
    PolishedTopazRod("POLISHED_TOPAZ_ROD", { Gear(it.raw) }),
    PortableCampfire("PORTABLE_CAMPFIRE"),
    PowerCrystal("POWER_CRYSTAL"),
    RefinedDiamond("REFINED_DIAMOND"),
    RefinedMithril("REFINED_MITHRIL"),
    RefinedTitanium("REFINED_TITANIUM"),
    RefinedTungsten("REFINED_TUNGSTEN"),
    RefinedUmber("REFINED_UMBER"),
    ReinforcedChisel("REINFORCED_CHISEL", { Gear(it.raw) }),
    RubyDrillTX15("GEMSTONE_DRILL_1", { Gear(it.raw) }),
    SecretRailroadPass("SECRET_RAILROAD_PASS"),
    SkeletonKey("SKELETON_KEY"),
    TitaniumDrillDRX355("TITANIUM_DRILL_1", { Gear(it.raw) }),
    TitaniumDrillDRX455("TITANIUM_DRILL_2", { Gear(it.raw) }),
    TitaniumDrillDRX555("TITANIUM_DRILL_3", { Gear(it.raw) }),
    TitaniumDrillDRX655("TITANIUM_DRILL_4", { Gear(it.raw) }),
    TopazDrillKGR12("GEMSTONE_DRILL_3", { Gear(it.raw) }),
    TungstenKey("TUNGSTEN_KEY"),
    TungstenPlate("TUNGSTEN_PLATE"),
    TungstenRegulator("TUNGSTEN_KEYCHAIN"),
    UmberKey("UMBER_KEY"),
    UmberPlate("UMBER_PLATE");

    constructor(apiName: String) : this(apiName, { it })
}