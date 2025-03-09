package net.dungeonhub.hypixel.entities.inventory.items.id

import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

enum class ConsumableItemId(override val apiName: String, override val itemClass: ((SkyblockItem) -> SkyblockItem)) :
    KnownSkyblockItemId {
    DwarvenOsBlockBran("DWARVEN_OS_BLOCK_BRAN"),
    DwarvenOsGemstoneGrahams("DWARVEN_OS_GEMSTONE_GRAHAMS"),
    DwarvenOsMetallicMinis("DWARVEN_OS_METALLIC_MINIS"),
    DwarvenOsOreOats("DWARVEN_OS_ORE_OATS"),
    ToxicArrowPoison("TOXIC_ARROW_POISON"),
    TwilightArrowPoison("TWILIGHT_ARROW_POISON"),
    BlessedBait("BLESSED_BAIT"),
    CarrotBait("CARROT_BAIT"),
    CorruptedBait("CORRUPTED_BAIT"),
    DarkBait("DARK_BAIT"),
    FishBait("FISH_BAIT"),
    FrozenBait("FROZEN_BAIT"),
    GlowyChumBait("GLOWY_CHUM_BAIT"),
    GoldenBait("GOLDEN_BAIT"),
    HotBait("HOT_BAIT"),
    IceBait("ICE_BAIT"),
    LightBait("LIGHT_BAIT"),
    MinnowBait("MINNOW_BAIT"),
    SharkBait("SHARK_BAIT"),
    SpikedBait("SPIKED_BAIT"),
    SpookyBait("SPOOKY_BAIT"),
    WhaleBait("WHALE_BAIT"),
    WormBait("WORM_BAIT");

    constructor(apiName: String) : this(apiName, { it })
}