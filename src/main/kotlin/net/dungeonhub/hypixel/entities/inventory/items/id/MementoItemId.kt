package net.dungeonhub.hypixel.entities.inventory.items.id

import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem
import net.dungeonhub.hypixel.entities.inventory.items.special.BasketOfHopeMemento
import net.dungeonhub.hypixel.entities.inventory.items.special.BingosSecretMemento
import net.dungeonhub.hypixel.entities.inventory.items.special.CenturyMemento
import net.dungeonhub.hypixel.entities.inventory.items.special.Memento

enum class MementoItemId(override val apiName: String, override val itemClass: ((SkyblockItem) -> SkyblockItem)) :
    KnownSkyblockItemId {
    BasketOfHope("POTATO_BASKET", { BasketOfHopeMemento(it.raw) }),
    BingosSecrets("SECRET_BINGO_MEMENTO", { BingosSecretMemento(it.raw) }),
    CakeSliceOfTheCenturyBlue("CENTURY_MEMENTO_BLUE", { CenturyMemento(it.raw) }),
    CakeSliceOfTheCenturyGreen("CENTURY_MEMENTO_GREEN", { CenturyMemento(it.raw) }),
    CakeSliceOfTheCenturyPink("CENTURY_MEMENTO_PINK", { CenturyMemento(it.raw) }),
    CakeSliceOfTheCenturyRed("CENTURY_MEMENTO_RED", { CenturyMemento(it.raw) }),
    CakeSliceOfTheCenturyYellow("CENTURY_MEMENTO_YELLOW", { CenturyMemento(it.raw) }),
    CampaignPoster("CAMPAIGN_POSTER", { Memento(it.raw) }),
    CoinsOnFire("BURNING_COINS", { Memento(it.raw) }), //TODO is this rather an admin item?
    DreamspireTorch("DREAMSPIRE_TORCH", { Memento(it.raw) }),
    ExpensiveToy("EXPENSIVE_TOY", { Memento(it.raw) }),
    FrenchBread("FRENCH_BREAD", { Memento(it.raw) }),
    GoldenCollar("GOLDEN_COLLAR", { Memento(it.raw) }),
    LockedBallotBox("LOCKED_BALLOT_BOX", { Memento(it.raw) }),
    MoldyMuffin("MOLDY_MUFFIN", { Memento(it.raw) }),
    PaintersPalette("PAINTERS_PALETTE", { Memento(it.raw) }),
    PieceOfWizardPortal("WIZARD_PORTAL_MEMENTO", { Memento(it.raw) }),
    ShinyRelic("SHINY_RELIC", { Memento(it.raw) }),
    ThereAndBackAgain("RIFT_COMPLETION_MEMENTO", { Memento(it.raw) }),
    MediocreDrawingOfAFish("MEDIOCRE_FISH_DRAWING", { Memento(it.raw) }),
    SplendidDrawingOfAFish("SPLENDID_FISH_DRAWING", { Memento(it.raw) });

    constructor(apiName: String) : this(apiName, { it })
}