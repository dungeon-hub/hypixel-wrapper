package net.dungeonhub.hypixel.entities.skyblock.pet

//TODO complete mapping
enum class KnownPetItem(override val apiName: String, displayName: String? = null) : PetItem {
    //Exp Boosts
    MiningExpCommon("PET_ITEM_MINING_SKILL_BOOST_COMMON"),
    MiningExpUncommon("PET_ITEM_MINING_SKILL_BOOST_UNCOMMON"),
    MiningExpRare("PET_ITEM_MINING_SKILL_BOOST_RARE"),
    FarmingExpCommon("PET_ITEM_FARMING_SKILL_BOOST_COMMON"),
    FarmingExpUncommon("PET_ITEM_FARMING_SKILL_BOOST_UNCOMMON"),
    FarmingExpRare("PET_ITEM_FARMING_SKILL_BOOST_RARE"),
    FarmingExpEpic("PET_ITEM_FARMING_SKILL_BOOST_EPIC"),
    FishingExpCommon("PET_ITEM_FISHING_SKILL_BOOST_COMMON"),
    FishingExpUncommon("PET_ITEM_FISHING_SKILL_BOOST_UNCOMMON"),
    FishingExpRare("PET_ITEM_FISHING_SKILL_BOOST_RARE"),
    FishingExpEpic("PET_ITEM_FISHING_SKILL_BOOST_EPIC"),
    CombatExpCommon("PET_ITEM_COMBAT_SKILL_BOOST_COMMON"),
    CombatExpUncommon("PET_ITEM_COMBAT_SKILL_BOOST_UNCOMMON"),
    CombatExpRare("PET_ITEM_COMBAT_SKILL_BOOST_RARE"),
    CombatExpEpic("PET_ITEM_COMBAT_SKILL_BOOST_EPIC"),
    ForagingExpCommon("PET_ITEM_FORAGING_SKILL_BOOST_COMMON"),
    ForagingExpEpic("PET_ITEM_FORAGING_SKILL_BOOST_EPIC"),
    AllSkillsExp("PET_ITEM_ALL_SKILLS_BOOST_COMMON"),
    AllSkillsExpSuperBoost("ALL_SKILLS_SUPER_BOOST"),

    //Stats Items
    BigTeeth("PET_ITEM_BIG_TEETH_COMMON"),
    BiggerTeeth("BIGGER_TEETH"),
    IronClaws("PET_ITEM_IRON_CLAWS_COMMON"),
    GoldClaws("GOLD_CLAWS"),
    HardenedScales("PET_ITEM_HARDENED_SCALES_UNCOMMON"),
    ReinforcedScales("REINFORCED_SCALES"),
    SharpenedClaws("PET_ITEM_SHARPENED_CLAWS_UNCOMMON"),
    SerratedClaws("SERRATED_CLAWS"),
    SpookyCupcake("PET_ITEM_SPOOKY_CUPCAKE"),
    QuickClaw("PET_ITEM_QUICK_CLAW"),
    YellowBandana("YELLOW_BANDANA"),
    GreenBandana("GREEN_BANDANA"),
    BrownBandana("BROWN_BANDANA"),
    AntiqueRemedies("ANTIQUE_REMEDIES"),
    CrochetTigerPlushie("CROCHET_TIGER_PLUSHIE"),
    MinosRelic("MINOS_RELIC"),
    WashedUpSouvenir("WASHED_UP_SOUVENIR"),
    LuckyClover("PET_ITEM_LUCKY_CLOVER"),
    Textbook("PET_ITEM_TEXTBOOK"),
    ReaperGem("REAPER_GEM"),
    BejeweledCollar("BEJEWELED_COLLAR"),
    TitaniumMinecart("PET_ITEM_TITANIUM_MINECART"),

    //Perk Items
    DwarfTurtleShelmet("DWARF_TURTLE_SHELMET"),
    Bubblegum("PET_ITEM_BUBBLEGUM"),
    ExpShare("PET_ITEM_EXP_SHARE"),
    TierBoost("PET_ITEM_TIER_BOOST"),

    //Pet specific
    Saddle("PET_ITEM_SADDLE"),
    FlyingPig("PET_ITEM_FLYING_PIG"),
    FourEyedFish("FOUR_EYED_FISH"),
    DeadCatFood("DEAD_CAT_FOOD"),
    GrandmasKnittingNeedle("GRANDMAS_KNITTING_NEEDLE"),

    //Mythic upgrade
    VampireFang("PET_ITEM_VAMPIRE_FANG"),
    Jerry3DGlasses("PET_ITEM_TOY_JERRY"),
    RadioactiveVial("RADIOACTIVE_VIAL"),
    EndermanCortexRewriter("ENDERMAN_CORTEX_REWRITER"),
    RatJetpack("RAT_JETPACK"),
    FakeNeuroscienceDegree("FAKE_NEUROSCIENCE_DEGREE"),
    GuardianLuckyBlock("GUARDIAN_LUCKY_BLOCK"),
    BlackWoolenYarn("BLACK_WOOLEN_YARN"),
    PureMithrilGem("PET_ITEM_PURE_MITHRIL_GEM"),
    MagicTopHat("MAGIC_TOP_HAT"),
    MixedMiteGel("MIXED_MITE_GEL"),

    //Cosmetic
    UncommonPartyHat("UNCOMMON_PARTY_HAT");

    val displayName = displayName ?: name.replace(Regex("([A-Z])"), " $1").trim()

    class UnknownPetItem(override val apiName: String) : PetItem

    companion object {
        fun fromApiName(apiName: String): PetItem {
            return KnownPetItem.entries.firstOrNull { it.apiName == apiName }
                ?: UnknownPetItem(apiName)
        }
    }
}