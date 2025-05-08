package net.dungeonhub.hypixel.entities.skyblock.price.calculator

import net.dungeonhub.hypixel.entities.inventory.items.ItemWithAbility
import net.dungeonhub.hypixel.entities.inventory.items.id.KnownAbilityScrollId
import net.dungeonhub.hypixel.entities.skyblock.price.PriceHelper

object AbilityItemPriceCalculator : PriceCalculator<ItemWithAbility> {
    override val type = ItemWithAbility::class.java

    override fun calculatePrice(item: ItemWithAbility): Long {
        val abilityScroll = item.abilityScroll

        if (abilityScroll != null && abilityScroll is KnownAbilityScrollId) {
            return PriceHelper.getAppliedPrice(abilityScroll)
        }

        return 0
    }
}