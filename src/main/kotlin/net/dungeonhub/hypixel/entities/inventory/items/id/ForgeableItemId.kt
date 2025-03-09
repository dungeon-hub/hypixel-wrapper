package net.dungeonhub.hypixel.entities.inventory.items.id

import net.dungeonhub.hypixel.entities.inventory.items.Gear
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

enum class ForgeableItemId(override val apiName: String, override val itemClass: ((SkyblockItem) -> SkyblockItem)) :
    KnownSkyblockItemId {
    BejeweledHandle("BEJEWELED_HANDLE"),
    Chisel("CHISEL", { Gear(it.raw) }),
    DivansPowderCoating("DIVAN_POWDER_COATING"),
    DrillMotor("DRILL_ENGINE"),
    FuelCanister("FUEL_TANK"),
    GemstoneChamber("GEMSTONE_CHAMBER"),
    GemstoneMixture("GEMSTONE_MIXTURE"),
    GlaciteAmalgamation("GLACITE_AMALGAMATION"),
    GlacitePlatedChisel("GLACITE_CHISEL", { Gear(it.raw) }),
    GoldenPlate("GOLDEN_PLATE"),
    MithrilPlate("MITHRIL_PLATE"),
    PerfectChisel("PERFECT_CHISEL", { Gear(it.raw) }),
    PerfectPlate("PERFECT_PLATE"),
    PortableCampfire("PORTABLE_CAMPFIRE"),
    PowerCrystal("POWER_CRYSTAL"),
    RefinedDiamond("REFINED_DIAMOND"),
    RefinedMithril("REFINED_MITHRIL"),
    RefinedTitanium("REFINED_TITANIUM"),
    RefinedTungsten("REFINED_TUNGSTEN"),
    RefinedUmber("REFINED_UMBER"),
    ReinforcedChisel("REINFORCED_CHISEL", { Gear(it.raw) }),
    SecretRailroadPass("SECRET_RAILROAD_PASS"),
    SkeletonKey("SKELETON_KEY"),
    TungstenKey("TUNGSTEN_KEY"),
    TungstenPlate("TUNGSTEN_PLATE"),
    TungstenRegulator("TUNGSTEN_KEYCHAIN"),
    UmberKey("UMBER_KEY"),
    UmberPlate("UMBER_PLATE");

    constructor(apiName: String) : this(apiName, { it })
}