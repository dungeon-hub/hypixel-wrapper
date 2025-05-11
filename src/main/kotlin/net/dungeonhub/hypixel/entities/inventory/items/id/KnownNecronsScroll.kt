package net.dungeonhub.hypixel.entities.inventory.items.id

import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

enum class KnownNecronsScroll(
    override val apiName: String,
    override val itemClass: ((SkyblockItem) -> SkyblockItem)
) : KnownSkyblockItemId, NecronsScrollId {
    Implosion("IMPLOSION_SCROLL"),
    ShadowWarp("SHADOW_WARP_SCROLL"),
    WitherShield("WITHER_SHIELD_SCROLL");

    constructor(apiName: String) : this(apiName, { it })

    class UnknownNecronsScroll(override val apiName: String) : NecronsScrollId

    companion object {
        fun fromApiName(apiName: String): NecronsScrollId {
            return KnownNecronsScroll.entries.firstOrNull { it.apiName == apiName }
                ?: UnknownNecronsScroll(apiName)
        }
    }
}