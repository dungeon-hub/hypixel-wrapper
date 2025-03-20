package net.dungeonhub.hypixel.entities.inventory.items.id

import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

enum class MementoItemId(override val apiName: String, override val itemClass: ((SkyblockItem) -> SkyblockItem)) :
    KnownSkyblockItemId {
    BasketOfHope("POTATO_BASKET"),
    BingosSecrets("SECRET_BINGO_MEMENTO"),
    CakeSliceOfTheCenturyBlue("CENTURY_MEMENTO_BLUE"),
    CakeSliceOfTheCenturyGreen("CENTURY_MEMENTO_GREEN"),
    CakeSliceOfTheCenturyPink("CENTURY_MEMENTO_PINK"),
    CakeSliceOfTheCenturyRed("CENTURY_MEMENTO_RED"),
    CakeSliceOfTheCenturyYellow("CENTURY_MEMENTO_YELLOW"),
    CampaignPoster("CAMPAIGN_POSTER"),
    CoinsOnFire("BURNING_COINS"), //TODO is this rather an admin item?
    ExpensiveToy("EXPENSIVE_TOY"),
    FrenchBread("FRENCH_BREAD"),
    GoldenCollar("GOLDEN_COLLAR"),
    LockedBallotBox("LOCKED_BALLOT_BOX"),
    MoldyMuffin("MOLDY_MUFFIN"),
    PaintersPalette("PAINTERS_PALETTE"),
    PieceOfWizardPortal("WIZARD_PORTAL_MEMENTO"),
    ShinyRelic("SHINY_RELIC"),
    ThereAndBackAgain("RIFT_COMPLETION_MEMENTO"),
    MediocreDrawingOfAFish("MEDIOCRE_FISH_DRAWING"),
    SplendidDrawingOfAFish("SPLENDID_FISH_DRAWING");

    constructor(apiName: String) : this(apiName, { it })
}