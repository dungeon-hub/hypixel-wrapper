package net.dungeonhub.hypixel.entities.inventory.items.id

import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

enum class RiftTimecharmId(override val apiName: String, override val itemClass: ((SkyblockItem) -> SkyblockItem)) :
    KnownSkyblockItemId {
    CelestialTimecharm("RIFT_TROPHY_MOUNTAIN"),
    ChickenNEggTimecharm("RIFT_TROPHY_CHICKEN_N_EGG"),
    GlobulateTimecharm("RIFT_TROPHY_SLIME"),
    LivingTimecharm("RIFT_TROPHY_LAZY_LIVING"),
    MirrorverseTimecharm("RIFT_TROPHY_MIRRORED"),
    SkyBlockCitizenTimecharm("RIFT_TROPHY_CITIZEN"),
    SupremeTimecharm("RIFT_TROPHY_WYLDLY_SUPREME"),
    VampiricTimecharm("RIFT_TROPHY_VAMPIRIC");

    constructor(apiName: String) : this(apiName, { it })
}