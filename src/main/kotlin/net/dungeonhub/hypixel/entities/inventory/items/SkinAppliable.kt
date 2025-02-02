package net.dungeonhub.hypixel.entities.inventory.items

interface SkinAppliable : SkyblockItemFactory {
    val appliedDye: String?
        get() = extraAttributes.getString("dye_item")

    val appliedSkin: String?
        get() = extraAttributes.getString("skin")
}