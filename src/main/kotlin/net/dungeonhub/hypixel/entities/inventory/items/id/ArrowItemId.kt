package net.dungeonhub.hypixel.entities.inventory.items.id

import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

enum class ArrowItemId(override val apiName: String, override val itemClass: ((SkyblockItem) -> SkyblockItem)) :
    KnownSkyblockItemId {
    FlintArrow("ARROW"),
    ReinforcedIronArrow("REINFORCED_IRON_ARROW"),
    GoldTippedArrow("GOLD_TIPPED_ARROW"),
    RedstoneTippedArrow("REDSTONE_TIPPED_ARROW"),
    EmeraldTippedArrows("EMERALD_TIPPED_ARROW"),
    BouncyArrow("BOUNCY_ARROW"),
    IcyArrow("ICY_ARROW"),
    ArmorshredArrow("ARMORSHRED_ARROW"),
    ExplosiveArrow("EXPLOSIVE_ARROW"),
    GlueArrow("GLUE_ARROW"),
    NansorbArrow("NANSORB_ARROW"),
    MagmaArrow("MAGMA_ARROW");

    constructor(apiName: String) : this(apiName, { it })
}