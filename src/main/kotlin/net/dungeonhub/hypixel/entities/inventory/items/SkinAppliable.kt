package net.dungeonhub.hypixel.entities.inventory.items

import net.dungeonhub.hypixel.entities.inventory.items.id.DyeId
import net.dungeonhub.hypixel.entities.inventory.items.id.KnownDyeId

interface SkinAppliable : SkyblockItemFactory {
    val appliedDye: DyeId?
        get() = extraAttributes.getString("dye_item")?.let { KnownDyeId.fromApiName(it) }

    val appliedSkin: String?
        get() = extraAttributes.getString("skin")
}