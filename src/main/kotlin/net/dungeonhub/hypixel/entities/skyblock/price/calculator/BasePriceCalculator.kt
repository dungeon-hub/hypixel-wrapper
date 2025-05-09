package net.dungeonhub.hypixel.entities.skyblock.price.calculator

import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItemFactory

interface BasePriceCalculator {
    fun appliesToItem(item: SkyblockItemFactory): Boolean

    fun calculateBasePrice(item: SkyblockItemFactory): Long

    companion object {
        val calculators = listOf<BasePriceCalculator>(
            AbilityItemPriceCalculator,
            AccessoryPriceCalculator,
            ArmorOfDivanPriceCalculator,
            ArmorPriceCalculator,
            AttributeItemPriceCalculator,
            CombatBookItemPriceCalculator,
            DeployablePriceCalculator,
            EnchantableItemPriceCalculator,
            FishingRodPriceCalculator,
            GearPriceCalculator,
            GemItemPriceCalculator,
            HotPotatoBookPriceCalculator,
            ItemPetPriceCalculator,
            ManaDisintegratablePriceCalculator,
            MidasWeaponPriceCalculator,
            PowderCoatableItemPriceCalculator,
            RuneItemPriceCalculator,
            ShensAuctionItemPriceCalculator,
            SkinAppliablePriceCalculator,
            SkyblockItemPriceCalculator,
            SwordPriceCalculator,
            TeleportationSwordPriceCalculator
        )
    }
}