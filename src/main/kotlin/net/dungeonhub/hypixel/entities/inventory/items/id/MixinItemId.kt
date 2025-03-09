package net.dungeonhub.hypixel.entities.inventory.items.id

import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

enum class MixinItemId(override val apiName: String, override val itemClass: ((SkyblockItem) -> SkyblockItem)) :
    KnownSkyblockItemId {
    ZombieBrainMixin("ZOMBIE_BRAIN_MIXIN"),
    WolfFurMixin("WOLF_FUR_MIXIN"),
    SpiderEggMixin("SPIDER_EGG_MIXIN"),
    EndPortalFumes("END_PORTAL_FUMES_MIXIN"),
    GabagoeyMixin("GABAGOEY_MIXIN"),
    DeepterrorMixin("DEEPTERROR_MIXIN"),
    GlowingMushMixin("MUSHED_MUSHROOM_MIXIN"),
    HotChocolateMixin("HOT_CHOCOLATE_MIXIN");

    constructor(apiName: String) : this(apiName, { it })
}
