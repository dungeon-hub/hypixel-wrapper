package net.dungeonhub.hypixel.entities.inventory.items.id

import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem
import net.dungeonhub.hypixel.entities.inventory.items.special.Mandraa

enum class PowerStoneId(override val apiName: String, override val itemClass: ((SkyblockItem) -> SkyblockItem)) :
    KnownSkyblockItemId {
    AcaciaBirdhouse("ACACIA_BIRDHOUSE"),
    BeatingHeart("BEATING_HEART"),
    BubbaBlister("BUBBA_BLISTER"),
    DarkOrb("DARK_ORB"),
    DisplacedLeech("DISPLACED_LEECH"),
    EccentricPainting("ECCENTRIC_PAINTING"),
    EndStoneShulker("END_STONE_SHULKER"),
    EnderMonocle("ENDER_MONOCLE"),
    FangtasticChocolateChip("CHOCOLATE_CHIP"),
    Furball("FURBALL"),
    GlaciteShard("GLACITE_SHARD"),
    HazmatEnderman("HAZMAT_ENDERMAN"),
    HornsOfTorment("HORNS_OF_TORMENT"),
    LuxuriousSpool("LUXURIOUS_SPOOL"),
    MagmaUrchin("MAGMA_URCHIN"),
    Mandraa("MANDRAA", { Mandraa(it.raw) }),
    ObsidianTablet("OBSIDIAN_TABLET"),
    PreciousPearl("PRECIOUS_PEARL"),
    RockCandy("ROCK_CANDY"),
    ScorchedBooks("SCORCHED_BOOKS"),
    VitaminDeath("VITAMIN_DEATH");

    constructor(apiName: String) : this(apiName, { it })
}