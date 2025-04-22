package net.dungeonhub.hypixel.entities.inventory.items.id

import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

enum class KnownDyeId(override val apiName: String, override val itemClass: ((SkyblockItem) -> SkyblockItem)) :
    KnownSkyblockItemId, DyeId {
    AquamarineDye("DYE_AQUAMARINE"),
    ArchfiendDye("DYE_ARCHFIEND"),
    AuroraDye("DYE_AURORA"),
    BingoBlueDye("DYE_BINGO_BLUE"),
    BlackIceDye("DYE_BLACK_ICE"),
    BoneDye("DYE_BONE"),
    BrickRedDye("DYE_BRICK_RED"),
    ByzantiumDye("DYE_BYZANTIUM"),
    CarmineDye("DYE_CARMINE"),
    CeladonDye("DYE_CELADON"),
    CelesteDye("DYE_CELESTE"),
    ChocolateDye("DYE_CHOCOLATE"),
    CopperDye("DYE_COPPER"),
    CyclamenDye("DYE_CYCLAMEN"),
    DarkPurpleDye("DYE_DARK_PURPLE"),
    DungDye("DYE_DUNG"),
    EmeraldDye("DYE_EMERALD"),
    FlameDye("DYE_FLAME"),
    FossilDye("DYE_FOSSIL"),
    FrostbittenDye("DYE_FROSTBITTEN"),
    HollyDye("DYE_HOLLY"),
    IcebergDye("DYE_ICEBERG"),
    JadeDye("DYE_JADE"),
    LavaDye("DYE_LAVA"),
    LividDye("DYE_LIVID"),
    LuckyDye("DYE_LUCKY"),
    MangoDye("DYE_MANGO"),
    MatchaDye("DYE_MATCHA"),
    MidnightDye("DYE_MIDNIGHT"),
    MochaDye("DYE_MOCHA"),
    NadeshikoDye("DYE_NADESHIKO"),
    NecronDye("DYE_NECRON"),
    NyanzaDye("DYE_NYANZA"),
    OasisDye("DYE_OASIS"),
    OceanDye("DYE_OCEAN"),
    PastelSkyDye("DYE_PASTEL_SKY"),
    PearlescentDye("DYE_PEARLESCENT"),
    PeltDye("DYE_PELT"),
    PeriwinkleDye("DYE_PERIWINKLE"),
    PortalDye("DYE_PORTAL"),
    PureBlackDye("DYE_PURE_BLACK"),
    PureBlueDye("DYE_PURE_BLUE"),
    PureWhiteDye("DYE_PURE_WHITE"),
    PureYellowDye("DYE_PURE_YELLOW"),
    RoseDye("DYE_ROSE"),
    SangriaDye("DYE_SANGRIA"),
    SecretDye("DYE_SECRET"),
    SnowflakeDye("DYE_SNOWFLAKE"),
    SunsetDye("DYE_SUNSET"),
    TentacleDye("TENTACLE_DYE"),
    WardenDye("DYE_WARDEN"),
    WildStrawberryDye("DYE_WILD_STRAWBERRY");

    constructor(apiName: String) : this(apiName, { it })

    class UnknownDyeId(override val apiName: String) : DyeId

    companion object {
        fun fromApiName(apiName: String): DyeId {
            return KnownDyeId.entries.firstOrNull { it.apiName == apiName } ?: UnknownDyeId(apiName)
        }
    }
}