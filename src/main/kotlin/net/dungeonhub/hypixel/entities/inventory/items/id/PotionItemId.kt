package net.dungeonhub.hypixel.entities.inventory.items.id

import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem
import net.dungeonhub.hypixel.entities.inventory.items.special.Potion

enum class PotionItemId(override val apiName: String, override val itemClass: ((SkyblockItem) -> SkyblockItem)) :
    KnownSkyblockItemId {
    BitterIcedTea("BITTER_ICE_TEA"),
    BlackCoffee("BLACK_COFFEE"),
    CheapCoffee("CHEAP_COFFEE"),
    DctrPaper("DR_PAPER"),
    DecentCoffee("DECENT_COFFEE"),
    HotChocolate("HOT_CHOCOLATE"),
    KnockOffCola("KNOCKOFF_COLA"),
    MarshSporeSoup("MARSH_SPORE_SOUP"),
    Potion("POTION", { Potion(it.raw) }),
    PulpousOrangeJuice("PULPOUS_ORANGE_JUICE"),
    RedThornleafTea("RED_THORNLEAF_TEA"),
    ScarletonPremium("SCARLETON_PREMIUM"),
    SlayerEnergyDrink("SLAYER_ENERGY_DRINK"),
    TepidGreenTea("TEPID_GREEN_TEA"),
    TuttiFruttiFlavoredPoison("TUTTI_FRUTTI_POISON"),
    VikingsTear("VIKING_TEAR");

    constructor(apiName: String) : this(apiName, { it })
}