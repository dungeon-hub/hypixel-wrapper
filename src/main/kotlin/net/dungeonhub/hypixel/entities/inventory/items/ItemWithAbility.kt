package net.dungeonhub.hypixel.entities.inventory.items

interface ItemWithAbility : SkyblockItemFactory {
    //TODO map to scroll type?
    val abilityScroll: String?
        get() = extraAttributes.getString("power_ability_scroll")
}