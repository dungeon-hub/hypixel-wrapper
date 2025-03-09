package net.dungeonhub.hypixel.entities.inventory.items.id

import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

enum class MementoItemId(override val apiName: String, override val itemClass: ((SkyblockItem) -> SkyblockItem)) :
    KnownSkyblockItemId {
    BasketOfHope("POTATO_BASKET"),
    BingosSecrets("SECRET_BINGO_MEMENTO"),
    CakeSliceOfTheCentury("CENTURY_MEMENTO_RED"),
    CampaignPoster("CAMPAIGN_POSTER"),
    ExpensiveToy("EXPENSIVE_TOY"),
    FrenchBread("FRENCH_BREAD"),
    GoldenCollar("GOLDEN_COLLAR"),
    LockedBallotBox("LOCKED_BALLOT_BOX"),
    MoldyMuffin("MOLDY_MUFFIN"),
    PaintersPalette("PAINTERS_PALETTE"),
    PieceOfWizardPortal("WIZARD_PORTAL_MEMENTO"),
    ShinyRelic("SHINY_RELIC"),
    ThereAndBackAgain("RIFT_COMPLETION_MEMENTO");

    constructor(apiName: String) : this(apiName, { it })
}