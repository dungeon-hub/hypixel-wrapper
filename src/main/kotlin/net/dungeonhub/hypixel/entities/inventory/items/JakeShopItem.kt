package net.dungeonhub.hypixel.entities.inventory.items

interface JakeShopItem : SkyblockItemFactory {
    val requiredEntity: String?
        get() = extraAttributes.getString("entity_required")
}