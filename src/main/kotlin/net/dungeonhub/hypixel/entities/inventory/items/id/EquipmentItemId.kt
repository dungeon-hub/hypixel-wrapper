package net.dungeonhub.hypixel.entities.inventory.items.id

import net.dungeonhub.hypixel.entities.inventory.items.Gear
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem
import net.dungeonhub.hypixel.entities.inventory.items.special.*

enum class EquipmentItemId(override val apiName: String, override val itemClass: ((SkyblockItem) -> SkyblockItem)) :
    KnownSkyblockItemId {
    AdaptiveBelt("ADAPTIVE_BELT", { Gear(it.raw) }),
    AmberNecklace("AMBER_NECKLACE", { Gear(it.raw) }),
    AmethystGauntlet("AMETHYST_GAUNTLET", { Gear(it.raw) }),
    AncientCloak("ANCIENT_CLOAK", { Gear(it.raw) }),
    AnglerBelt("ANGLER_BELT"),
    AnglerCloak("ANGLER_CLOAK"),
    AnglerBracelet("ANGLER_BRACELET"),
    AnglerNecklace("ANGLER_NECKLACE"),
    AnnihilationCloak("ANNIHILATION_CLOAK", { Gear(it.raw) }),
    ArachnesBelt("ARACHNE_BELT", { Gear(it.raw) }),
    ArachnesCloak("ARACHNE_CLOAK", { Gear(it.raw) }),
    ArachnesGloves("ARACHNE_GLOVES", { Gear(it.raw) }),
    ArachnesNecklace("ARACHNE_NECKLACE", { Gear(it.raw) }),
    BackwaterBelt("BACKWATER_BELT", { Gear(it.raw) }),
    BackwaterCloak("BACKWATER_CLOAK", { Gear(it.raw) }),
    BackwaterGloves("BACKWATER_GLOVES", { Gear(it.raw) }),
    BackwaterNecklace("BACKWATER_NECKLACE", { Gear(it.raw) }),
    BalloonSnake("BALLOON_SNAKE", { Gear(it.raw) }),
    BlackBelt("DOJO_BLACK_BELT", { Gear(it.raw) }),
    BlazeBelt("BLAZE_BELT", { Gear(it.raw) }),
    BlueBelt("DOJO_BLUE_BELT", { Gear(it.raw) }),
    BoneNecklace("BONE_NECKLACE", { Gear(it.raw) }),
    BrownBelt("DOJO_BROWN_BELT", { Gear(it.raw) }),
    ClayBracelet("CLAY_BRACELET", { Gear(it.raw) }),
    ClownfishCloak("CLOWNFISH_CLOAK", { Gear(it.raw) }),
    DeliriumNecklace("DELIRIUM_NECKLACE", { Gear(it.raw) }),
    DemonslayerGauntlet("DEMONLORD_GAUNTLET", { Gear(it.raw) }),
    DestructionCloak("DESTRUCTION_CLOAK", { Gear(it.raw) }),
    DisinfestorGloves("DISINFESTOR_GLOVES", { Gear(it.raw) }),
    DragonfadeCloak("DRAGONFADE_CLOAK", { Gear(it.raw) }),
    DragonfuseGlove("DRAGONFUSE_GLOVE", { Gear(it.raw) }),
    DwarvenHandwarmers("DWARVEN_HANDWARMERS", { Gear(it.raw) }),
    EnderBelt("ENDER_BELT", { Gear(it.raw) }),
    EnderCloak("ENDER_CLOAK", { Gear(it.raw) }),
    EnderGauntlet("ENDER_GAUNTLET", { Gear(it.raw) }),
    EnderNecklace("ENDER_NECKLACE", { Gear(it.raw) }),
    EnigmaCloak("ENIGMA_CLOAK", { Gear(it.raw) }),
    FinwaveBelt("FINWAVE_BELT", { Gear(it.raw) }),
    FinwaveCloak("FINWAVE_CLOAK", { Gear(it.raw) }),
    FinwaveGloves("FINWAVE_GLOVES", { Gear(it.raw) }),
    FlamingFist("FLAMING_FIST", { Gear(it.raw) }),
    FrozenAmulet("FROZEN_AMULET", { Gear(it.raw) }),
    GauntletOfContagion("GAUNTLET_OF_CONTAGION", { Gear(it.raw) }),
    GhastCloak("GHAST_CLOAK", { Gear(it.raw) }),
    GillsplashBelt("GILLSPLASH_BELT", { Gear(it.raw) }),
    GillsplashCloak("GILLSPLASH_CLOAK", { Gear(it.raw) }),
    GillsplashGloves("GILLSPLASH_GLOVES", { Gear(it.raw) }),
    GlowstoneGauntlet("GLOWSTONE_GAUNTLET", { Gear(it.raw) }),
    GoldenBelt("GOLDEN_BELT", { Gear(it.raw) }),
    GreatSpookBelt("GREAT_SPOOK_BELT", { GreatSpookEquipment(it.raw) }),
    GreatSpookCloak("GREAT_SPOOK_CLOAK", { GreatSpookEquipment(it.raw) }),
    GreatSpookGloves("GREAT_SPOOK_GLOVES", { GreatSpookEquipment(it.raw) }),
    GreatSpookNecklace("GREAT_SPOOK_NECKLACE", { GreatSpookEquipment(it.raw) }),
    GreenBelt("DOJO_GREEN_BELT", { Gear(it.raw) }),
    IchthyicBelt("ICHTHYIC_BELT", { Gear(it.raw) }),
    IchthyicCloak("ICHTHYIC_CLOAK", { Gear(it.raw) }),
    IchthyicGloves("ICHTHYIC_GLOVES", { Gear(it.raw) }),
    ImplosionBelt("IMPLOSION_BELT", { Gear(it.raw) }),
    JadeBelt("JADE_BELT", { Gear(it.raw) }),
    KnuckleSandwich("KNUCKLE_SANDWICH", { KnuckleSandwich(it.raw) }),
    LavaShellNecklace("LAVA_SHELL_NECKLACE", { Gear(it.raw) }),
    LeechBelt("LEECH_BELT", { Gear(it.raw) }),
    LotusBelt("LOTUS_BELT", { Gear(it.raw) }),
    LotusBracelet("LOTUS_BRACELET", { Gear(it.raw) }),
    LotusCloak("LOTUS_CLOAK", { Gear(it.raw) }),
    LotusNecklace("LOTUS_NECKLACE", { Gear(it.raw) }),
    LuminousBracelet("LUMINOUS_BRACELET", { Gear(it.raw) }),
    MagmaLordGauntlet("MAGMA_LORD_GAUNTLET", { Gear(it.raw) }),
    MagmaNecklace("MAGMA_NECKLACE", { Gear(it.raw) }),
    MithrilBelt("MITHRIL_BELT", { Gear(it.raw) }),
    MithrilCloak("MITHRIL_CLOAK", { Gear(it.raw) }),
    MithrilGauntlet("MITHRIL_GAUNTLET", { Gear(it.raw) }),
    MithrilNecklace("MITHRIL_NECKLACE", { Gear(it.raw) }),
    MoltenBelt("MOLTEN_BELT", { KuudraEquipment(it.raw) }),
    MoltenBracelet("MOLTEN_BRACELET", { KuudraEquipment(it.raw) }),
    MoltenCloak("MOLTEN_CLOAK", { KuudraEquipment(it.raw) }),
    MoltenNecklace("MOLTEN_NECKLACE", { KuudraEquipment(it.raw) }),
    PartyBelt("PARTY_BELT", { Gear(it.raw) }),
    PartyCloak("PARTY_CLOAK", { Gear(it.raw) }),
    PartyGloves("PARTY_GLOVES", { Gear(it.raw) }),
    PartyNecklace("PARTY_NECKLACE", { Gear(it.raw) }),
    PeltBelt("PELT_BELT", { Gear(it.raw) }),
    PendantOfDivan("DIVAN_PENDANT", { PendantOfDivan(it.raw) }),
    PestVest("PEST_VEST", { Gear(it.raw) }),
    PesthuntersBelt("PESTHUNTERS_BELT", { Gear(it.raw) }),
    PesthuntersCloak("PESTHUNTERS_CLOAK", { Gear(it.raw) }),
    PesthuntersGloves("PESTHUNTERS_GLOVES", { Gear(it.raw) }),
    PesthuntersNecklace("PESTHUNTERS_NECKLACE", { Gear(it.raw) }),
    PrismarineNecklace("PRISMARINE_NECKLACE", { Gear(it.raw) }),
    RedBelt("RED_BELT", { Gear(it.raw) }),
    RiftNecklace("RIFT_NECKLACE_OUTSIDE", { Gear(it.raw) }),
    RiftNecklaceInsideRift("RIFT_NECKLACE_INSIDE", { Gear(it.raw) }),
    SapphireCloak("SAPPHIRE_CLOAK", { Gear(it.raw) }),
    ScourgeCloak("SCOURGE_CLOAK", { Gear(it.raw) }),
    ScovilleBelt("SCOVILLE_BELT", { Gear(it.raw) }),
    ShadowAssassinCloak("SHADOW_ASSASSIN_CLOAK", { Gear(it.raw) }),
    ShensRingalia("SHENS_RINGALIA", { Gear(it.raw) }),
    SilkriderSafetyBelt("SILKRIDER_SAFETY_BELT", { Gear(it.raw) }),
    SnowBelt("SNOW_BELT", { Gear(it.raw) }),
    SnowCloak("SNOW_CLOAK", { Gear(it.raw) }),
    SnowGloves("SNOW_GLOVES", { Gear(it.raw) }),
    SnowNecklace("SNOW_NECKLACE", { Gear(it.raw) }),
    SoulweaverGloves("SOULWEAVER_GLOVES", { Gear(it.raw) }),
    SpongeBelt("SPONGE_BELT", { Gear(it.raw) }),
    SynthesizerV1("SYNTHESIZER_V1", { SynthesizerV1(it.raw) }),
    SynthesizerV2("SYNTHESIZER_V2", { SynthesizerV2(it.raw) }),
    SynthesizerV3("SYNTHESIZER_V3", { SynthesizerV3(it.raw) }),
    TeraShellNecklace("TERA_SHELL_NECKLACE", { Gear(it.raw) }),
    ThunderboltNecklace("THUNDERBOLT_NECKLACE", { Gear(it.raw) }),
    TitaniumBelt("TITANIUM_BELT", { Gear(it.raw) }),
    TitaniumCloak("TITANIUM_CLOAK", { Gear(it.raw) }),
    TitaniumGauntlet("TITANIUM_GAUNTLET", { Gear(it.raw) }),
    TitaniumNecklace("TITANIUM_NECKLACE", { Gear(it.raw) }),
    UpgradedAdaptiveBelt("STARRED_ADAPTIVE_BELT", { Gear(it.raw) }),
    UpgradedBoneNecklace("STARRED_BONE_NECKLACE", { Gear(it.raw) }),
    UpgradedShadowAssassinCloak("STARRED_SHADOW_ASSASSIN_CLOAK", { Gear(it.raw) }),
    VanquishedBlazeBelt("VANQUISHED_BLAZE_BELT", { VanquishedBlazeBelt(it.raw) }),
    VanquishedGhastCloak("VANQUISHED_GHAST_CLOAK", { VanquishedGhastCloak(it.raw) }),
    VanquishedGlowstoneGauntlet("VANQUISHED_GLOWSTONE_GAUNTLET", { VanquishedGlowstoneGauntlet(it.raw) }),
    VanquishedMagmaNecklace("VANQUISHED_MAGMA_NECKLACE", { VanquishedMagmaNecklace(it.raw) }),
    VerminBelt("VERMIN_BELT", { Gear(it.raw) }),
    WhiteBelt("DOJO_WHITE_BELT", { Gear(it.raw) }),
    YellowBelt("DOJO_YELLOW_BELT", { Gear(it.raw) }),
    ZorrosCape("ZORROS_CAPE", { Gear(it.raw) });

    constructor(apiName: String) : this(apiName, { it })
}