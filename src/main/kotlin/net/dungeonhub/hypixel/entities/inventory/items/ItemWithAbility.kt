package net.dungeonhub.hypixel.entities.inventory.items

import net.dungeonhub.hypixel.entities.inventory.items.id.AbilityScrollId
import net.dungeonhub.hypixel.entities.inventory.items.id.KnownAbilityScrollId

interface ItemWithAbility : SkyblockItemFactory {
    val abilityScroll: AbilityScrollId?
        get() = extraAttributes.getString("power_ability_scroll")?.let(KnownAbilityScrollId::fromApiName)
}