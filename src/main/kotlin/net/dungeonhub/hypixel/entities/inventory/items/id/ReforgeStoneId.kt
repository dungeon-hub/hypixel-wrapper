package net.dungeonhub.hypixel.entities.inventory.items.id

import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem
import net.dungeonhub.hypixel.entities.inventory.items.special.DragonDrop
import net.dungeonhub.hypixel.entities.inventory.items.special.KuudraMandible

enum class ReforgeStoneId(override val apiName: String, override val itemClass: ((SkyblockItem) -> SkyblockItem)) :
    KnownSkyblockItemId {
    AmberMaterial("AMBER_MATERIAL"),
    AndesiteWhetstone("REFINED_AMBER"),
    BeadyEyes("BEADY_EYES"),
    BlackDiamond("ONYX"),
    BlazeWax("BLAZE_WAX"),
    BlazenSphere("BLAZEN_SPHERE"),
    BlessedFruit("BLESSED_FRUIT"),
    BooStone("BOO_STONE"),
    BulkyStone("BULKY_STONE"),
    BurrowingSpores("BURROWING_SPORES"),
    CalcifiedHeart("CALCIFIED_HEART"),
    CandyCorn("CANDY_CORN"),
    ClippedWings("CLIPPED_WINGS"),
    DeepSeaOrb("DEEP_SEA_ORB"),
    DiamondAtom("DIAMOND_ATOM"),
    Diamonite("DIAMONITE"),
    DirtBottle("DIRT_BOTTLE"),
    DragonClaw("DRAGON_CLAW", { DragonDrop(it.raw) }),
    DragonHorn("DRAGON_HORN", { DragonDrop(it.raw) }),
    DragonScale("DRAGON_SCALE", { DragonDrop(it.raw) }),
    DwarvenGeode("ROCK_GEMSTONE"),
    DwarvenTreasure("DWARVEN_TREASURE"),
    EndStoneGeode("ENDSTONE_GEODE"),
    EntropySuppressor("ENTROPY_SUPPRESSOR"),
    FloweringBouquet("FLOWERING_BOUQUET"),
    FrigidHusk("FRIGID_HUSK"),
    FrozenBauble("FROZEN_BAUBLE"),
    FullJawFangingKit("FULL_JAW_FANGING_KIT"),
    GiantTooth("GIANT_TOOTH"),
    GleamingCrystal("GLEAMING_CRYSTAL"),
    GoldenBall("GOLDEN_BALL"),
    HardenedWood("HARDENED_WOOD"),
    Jaderald("JADERALD"),
    JerryStone("JERRY_STONE"),
    KaleidoscopicMineral("SALMON_OPAL"),
    KuudraMandible("KUUDRA_MANDIBLE", { KuudraMandible(it.raw) }),
    LapisCrystal("LAPIS_CRYSTAL"),
    LargeWalnut("LARGE_WALNUT"),
    LuckyDice("LUCKY_DICE"),
    MeteorShard("METEOR_SHARD"),
    MidasJewel("MIDAS_JEWEL"),
    MoilLog("MOIL_LOG"),
    MoltenCube("MOLTEN_CUBE"),
    NecromancersBrooch("NECROMANCER_BROOCH"),
    OpticalLens("OPTICAL_LENS"),
    OverflowingTrashCan("OVERFLOWING_TRASH_CAN"),
    OvergrownGrass("OVERGROWN_GRASS"),
    PetrifiedStarfall("PETRIFIED_STARFALL"),
    PitchinKoi("PITCHIN_KOI"),
    PocketIceberg("POCKET_ICEBERG"),
    PrecursorGear("PRECURSOR_GEAR"),
    PremiumFlesh("PREMIUM_FLESH"),
    PresumedGallonOfRedPaint("PRESUMED_GALLON_OF_RED_PAINT"),
    PureMithril("PURE_MITHRIL"),
    RareDiamond("RARE_DIAMOND"),
    RedNose("RED_NOSE"),
    RedScarf("RED_SCARF"),
    RustyAnchor("RUSTY_ANCHOR"),
    SadansBrooch("SADAN_BROOCH"),
    SaltCube("SALT_CUBE"),
    ScorchedTopaz("HOT_STUFF"),
    SearingStone("SEARING_STONE"),
    ShinyPrism("SHINY_PRISM"),
    SkyMartBrochure("SKYMART_BROCHURE"),
    SpiritStone("SPIRIT_DECOY"),
    SqueakyToy("SQUEAKY_TOY"),
    SuspiciousVial("SUSPICIOUS_VIAL"),
    TerrysSnowglobe("TERRY_SNOWGLOBE"),
    TitaniumTesseract("TITANIUM_TESSERACT"),
    ToilLog("TOIL_LOG"),
    WarpedStone("AOTE_STONE"),
    WitherBlood("WITHER_BLOOD");

    constructor(apiName: String) : this(apiName, { it })
}