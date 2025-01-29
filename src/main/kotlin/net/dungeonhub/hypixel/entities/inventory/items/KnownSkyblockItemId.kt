package net.dungeonhub.hypixel.entities.inventory.items

//TODO load enum from SB endpoint? -> not possible really, only maybe as a test?
//TODO maybe add an item type to easier get the item class
enum class KnownSkyblockItemId(override val apiName: String, val itemClass: ((SkyblockItem) -> SkyblockItem)) :
    SkyblockItemId {
    //Accessory

    //Admin Item

    //Armor

    //Brewing Ingredient
    EnchantedCake("ENCHANTED_CAKE"),
    EnchantedRedstoneLamp("ENCHANTED_REDSTONE_LAMP"),
    FoulFlesh("FOUL_FLESH"),

    //Consumable

    //Cosmetic

    //Crafting Material

    //Drill Part

    //Equipment

    //Forgeable Item

    //Item Sack

    //Memento

    //Minion

    //Mixin

    //Pet

    //Potion

    //Power Stone

    //Reforge Stone

    //Rift Item

    //Rift Timecharm

    //Shears

    //Tool

    //Vanilla Item

    //Wand

    //Weapon
    PhantomRod("PHANTOM_ROD"),
    Shredder("THE_SHREDDER"),

    //Misc
    KuudraKey("KUUDRA_TIER_KEY"),
    HotKuudraKey("KUUDRA_HOT_TIER_KEY"),
    BurningKuudraKey("KUUDRA_BURNING_TIER_KEY"),
    FieryKuudraKey("KUUDRA_FIERY_TIER_KEY"),
    InfernalKuudraKey("KUUDRA_INFERNAL_TIER_KEY"),
    TarantulaSilk("TARANTULA_SILK"),


    BuildersWand("BUILDERS_WAND", { BuildersWand(it.raw) }),
    Hyperion("HYPERION", { WitherBlade(it.raw) }),
    Valkyrie("VALKYRIE", { WitherBlade(it.raw) }),
    Scylla("SCYLLA", { WitherBlade(it.raw) }),
    Astraea("ASTRAEA", { WitherBlade(it.raw) }),
    NecronBlade("NECRON_BLADE", { WitherBlade(it.raw) }),
    Terminator("TERMINATOR", { Terminator(it.raw) }),
    AspectOfTheVoid("ASPECT_OF_THE_VOID"),
    RodOfTheSea("ROD_OF_THE_SEA"),
    SkyblockMenu("SKYBLOCK_MENU"),
    GrandExpBottle("GRAND_EXP_BOTTLE"),
    TitanicExpBottle("TITANIC_EXP_BOTTLE"),
    SlugBoots("SLUG_BOOTS"),
    InfernoRod("INFERNO_ROD"),
    MoogmaLeggings("MOOGMA_LEGGINGS"),
    DivansDrill("DIVAN_DRILL"),
    Pickonimbus("PICKONIMBUS"),
    RoyalPigeon("ROYAL_PIGEON"),
    RoyalCompass("ROYAL_COMPASS"),
    DwarvenMetalDetector("DWARVEN_METAL_DETECTOR"),
    Treecapitator("TREECAPITATOR_AXE"),
    SkeletonKey("SKELETON_KEY"),
    IceSprayWand("ICE_SPRAY_WAND"),
    DaedalusAxe("DAEDALUS_AXE"),
    FireFreezeStaff("FIRE_FREEZE_STAFF"),
    Stonk("STONK_PICKAXE"),
    ImplosionBelt("IMPLOSION_BELT"),
    MoodyGrappleshot("MOODY_GRAPPLESHOT"),
    BobOmb("BOB_OMB"),
    Infinileap("INFINITE_SPIRIT_LEAP"),
    InfinityboomTNT("INFINITE_SUPERBOOM_TNT"),
    RunaansBow("RUNAANS_BOW"),
    AbiCase("ABICASE"),
    BalloonSnake("BALLOON_SNAKE"),
    AncestralSpade("ANCESTRAL_SPADE"),
    NecronsHandle("NECRON_HANDLE"),
    LunarPigmanSkin("PET_SKIN_PIGMAN_LUNAR_PIG"),
    FireVeilWand("FIRE_VEIL_WAND"),
    ThunderboltNecklace("THUNDERBOLT_NECKLACE"),
    PestVest("PEST_VEST"),
    Sprayonator("SPRAYONATOR"),
    CocoChopper("COCO_CHOPPER"),
    FungiCutter("FUNGI_CUTTER"),
    SqueakyMousemat("SQUEAKY_MOUSEMAT"),
    Pet("PET", { PetAsItem(it.raw) }),
    PoochSword("POOCH_SWORD"),
    SapphirePowerScroll("SAPPHIRE_POWER_SCROLL"),
    SorrowHelmet("SORROW_HELMET"),
    SorrowChestplate("SORROW_CHESTPLATE"),
    SorrowLeggings("SORROW_LEGGINGS"),
    SorrowBoots("SORROW_BOOTS"),
    PerfectChisel("PERFECT_CHISEL"),
    ScareFragment("SCARE_FRAGMENT"),
    ExpShareCore("PET_ITEM_EXP_SHARE_DROP"),
    NullEdge("NULL_EDGE"),
    NullBlade("NULL_BLADE"),
    JudgementCore("JUDGEMENT_CORE"),
    JumboBackpack("JUMBO_BACKPACK"),
    BookOfProgression("BOOK_OF_PROGRESSION"),
    FarmingTalisman("FARMING_TALISMAN"),
    VillageTalisman("VILLAGE_TALISMAN"),
    RodOfLegends("LEGEND_ROD"),
    AbiphoneXPlus("ABIPHONE_X_PLUS"),
    AbiphoneXPlusSpecialEdition("ABIPHONE_X_PLUS_SPECIAL_EDITION"),
    AbiphoneXIUltra("ABIPHONE_XI_ULTRA"),
    AbiphoneXIUltraStyle("ABIPHONE_XI_ULTRA_STYLE"),
    AbiphoneXIIMega("ABIPHONE_XII_MEGA"),
    AbiphoneXIIMegaColor("ABIPHONE_XII_MEGA_COLOR"),
    AbiphoneXIIIPro("ABIPHONE_XIII_PRO"),
    AbiphoneXIIIProGiga("ABIPHONE_XIII_PRO_GIGA"),
    AbiphoneXIVEnormous("ABIPHONE_XIV_ENORMOUS"),
    AbiphoneXIVEnormousPurple("ABIPHONE_XIV_ENORMOUS_PURPLE"),
    AbiphoneXIVEnormousBlack("ABIPHONE_XIV_ENORMOUS_BLACK"),
    AbiphoneFlipDragon("ABIPHONE_FLIP_DRAGON"),
    AbiphoneFlipNucleus("ABIPHONE_FLIP_NUCLEUS"),
    AbiphoneFlipVolcano("ABIPHONE_FLIP_VOLCANO"),
    AbiphoneBingo("ABINGOPHONE");

    constructor(apiName: String) : this(apiName, { it })

    class UnknownSkyblockItemId(override val apiName: String) : SkyblockItemId

    companion object {
        fun fromApiName(apiName: String): SkyblockItemId {
            return KnownSkyblockItemId.entries.firstOrNull { it.apiName == apiName } ?: UnknownSkyblockItemId(apiName)
        }
    }
}