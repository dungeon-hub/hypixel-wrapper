package net.dungeonhub.hypixel.entities.inventory.items.id

import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem
import net.dungeonhub.hypixel.entities.skyblock.pet.PetItem

//TODO maybe remove the ones that are unobtainable? -> pretty easy to do through a separate class?
enum class KnownPetItem(
    override val apiName: String,
    override val itemClass: ((SkyblockItem) -> SkyblockItem),
    displayName: String? = null
) : KnownSkyblockItemId, PetItem {
    //Exp Boosts
    MiningExpCommon("PET_ITEM_MINING_SKILL_BOOST_COMMON"),
    MiningExpUncommon("PET_ITEM_MINING_SKILL_BOOST_UNCOMMON"),
    MiningExpRare("PET_ITEM_MINING_SKILL_BOOST_RARE"),
    MiningExpEpic("PET_ITEM_MINING_SKILL_BOOST_EPIC"),
    MiningExpLegendary("PET_ITEM_MINING_SKILL_BOOST_LEGENDARY"),
    FarmingExpCommon("PET_ITEM_FARMING_SKILL_BOOST_COMMON"),
    FarmingExpUncommon("PET_ITEM_FARMING_SKILL_BOOST_UNCOMMON"),
    FarmingExpRare("PET_ITEM_FARMING_SKILL_BOOST_RARE"),
    FarmingExpEpic("PET_ITEM_FARMING_SKILL_BOOST_EPIC"),
    FarmingExpLegendary("PET_ITEM_FARMING_SKILL_BOOST_LEGENDARY"),
    FishingExpCommon("PET_ITEM_FISHING_SKILL_BOOST_COMMON"),
    FishingExpUncommon("PET_ITEM_FISHING_SKILL_BOOST_UNCOMMON"),
    FishingExpRare("PET_ITEM_FISHING_SKILL_BOOST_RARE"),
    FishingExpEpic("PET_ITEM_FISHING_SKILL_BOOST_EPIC"),
    FishingExpLegendary("PET_ITEM_FISHING_SKILL_BOOST_LEGENDARY"),
    CombatExpCommon("PET_ITEM_COMBAT_SKILL_BOOST_COMMON"),
    CombatExpUncommon("PET_ITEM_COMBAT_SKILL_BOOST_UNCOMMON"),
    CombatExpRare("PET_ITEM_COMBAT_SKILL_BOOST_RARE"),
    CombatExpEpic("PET_ITEM_COMBAT_SKILL_BOOST_EPIC"),
    CombatExpLegendary("PET_ITEM_COMBAT_SKILL_BOOST_LEGENDARY"),
    ForagingExpCommon("PET_ITEM_FORAGING_SKILL_BOOST_COMMON"),
    ForagingExpUncommon("PET_ITEM_FORAGING_SKILL_BOOST_UNCOMMON"),
    ForagingExpRare("PET_ITEM_FORAGING_SKILL_BOOST_RARE"),
    ForagingExpEpic("PET_ITEM_FORAGING_SKILL_BOOST_EPIC"),
    ForagingExpLegendary("PET_ITEM_FORAGING_SKILL_BOOST_LEGENDARY"),
    AllSkillsExpCommon("PET_ITEM_ALL_SKILLS_BOOST_COMMON"),
    AllSkillsExpSuperBoost("ALL_SKILLS_SUPER_BOOST"),
    AllSkillsExpUncommon("PET_ITEM_ALL_SKILLS_BOOST_UNCOMMON"),
    AllSkillsExpRare("PET_ITEM_ALL_SKILLS_BOOST_RARE"),
    AllSkillsExpEpic("PET_ITEM_ALL_SKILLS_BOOST_EPIC"),
    AllSkillsExpLegendary("PET_ITEM_ALL_SKILLS_BOOST_LEGENDARY"),

    //Stats Items
    BigTeethCommon("PET_ITEM_BIG_TEETH_COMMON"),
    BigTeethUncommon("PET_ITEM_BIG_TEETH_UNCOMMON"),
    BigTeethRare("PET_ITEM_BIG_TEETH_RARE"),
    BigTeethEpic("PET_ITEM_BIG_TEETH_EPIC"),
    BigTeethLegendary("PET_ITEM_BIG_TEETH_LEGENDARY"),
    BiggerTeeth("BIGGER_TEETH"),
    IronClawsCommon("PET_ITEM_IRON_CLAWS_COMMON"),
    IronClawsUncommon("PET_ITEM_IRON_CLAWS_UNCOMMON"),
    IronClawsRare("PET_ITEM_IRON_CLAWS_RARE"),
    IronClawsEpic("PET_ITEM_IRON_CLAWS_EPIC"),
    IronClawsLegendary("PET_ITEM_IRON_CLAWS_LEGENDARY"),
    GoldClaws("GOLD_CLAWS"),
    HardenedScalesCommon("PET_ITEM_HARDENED_SCALES_COMMON"),
    HardenedScalesUncommon("PET_ITEM_HARDENED_SCALES_UNCOMMON"),
    HardenedScalesRare("PET_ITEM_HARDENED_SCALES_RARE"),
    HardenedScalesEpic("PET_ITEM_HARDENED_SCALES_EPIC"),
    HardenedScalesLegendary("PET_ITEM_HARDENED_SCALES_LEGENDARY"),
    ReinforcedScales("REINFORCED_SCALES"),
    SharpenedClawsCommon("PET_ITEM_SHARPENED_CLAWS_COMMON"),
    SharpenedClawsUncommon("PET_ITEM_SHARPENED_CLAWS_UNCOMMON"),
    SharpenedClawsRare("PET_ITEM_SHARPENED_CLAWS_RARE"),
    SharpenedClawsEpic("PET_ITEM_SHARPENED_CLAWS_EPIC"),
    SharpenedClawsLegendary("PET_ITEM_SHARPENED_CLAWS_LEGENDARY"),
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
    PetSaddle("PET_ITEM_SADDLE"),
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

    constructor(apiName: String) : this(apiName, { it })

    val displayName = displayName ?: name.replace(Regex("([A-Z])"), " $1").trim()

    class UnknownPetItem(override val apiName: String) : PetItem

    companion object {
        fun fromApiName(apiName: String): PetItem {
            return KnownPetItem.entries.firstOrNull { it.apiName == apiName }
                ?: UnknownPetItem(apiName)
        }
    }
}