package net.dungeonhub.hypixel.entities.inventory.items.id

import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

enum class KnownRodPartId(override val apiName: String, override val itemClass: ((SkyblockItem) -> SkyblockItem)) :
    KnownSkyblockItemId, RodPartId {
    //Sinker
    ChumSinker("CHUM_SINKER"),
    FestiveSinker("FESTIVE_SINKER"),
    HotspotSinker("HOTSPOT_SINKER"),
    IcySinker("ICY_SINKER"),
    JunkSinker("JUNK_SINKER"),
    PrismarineSinker("PRISMARINE_SINKER"),
    SpongeSinker("SPONGE_SINKER"),
    StingySinker("STINGY_SINKER"),

    //Hook
    CommonHook("COMMON_HOOK"),
    HotspotHook("HOTSPOT_HOOK"),
    PhantomHook("PHANTOM_HOOK"),
    TreasureHook("TREASURE_HOOK"),

    //Line
    ShreddedLine("SHREDDED_LINE"),
    SpeedyLine("SPEEDY_LINE"),
    TitanLine("TITAN_LINE");

    constructor(apiName: String) : this(apiName, { it })

    class UnknownRodPartId(override val apiName: String) : RodPartId

    companion object {
        fun fromApiName(apiName: String): RodPartId {
            return entries.firstOrNull { it.apiName == apiName } ?: UnknownRodPartId(apiName)
        }
    }
}