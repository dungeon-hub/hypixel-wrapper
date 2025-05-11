package net.dungeonhub.hypixel.entities.skyblock.price.calculator

import net.dungeonhub.hypixel.entities.inventory.items.EnchantableItem
import net.dungeonhub.hypixel.entities.inventory.items.Enchantment
import net.dungeonhub.hypixel.entities.inventory.items.KnownEnchantment
import net.dungeonhub.hypixel.entities.inventory.items.id.MiscItemId
import net.dungeonhub.hypixel.entities.inventory.items.id.ToolItemId
import net.dungeonhub.hypixel.entities.inventory.items.id.WeaponItemId
import net.dungeonhub.hypixel.entities.inventory.items.special.EnchantedBook
import net.dungeonhub.hypixel.entities.skyblock.price.PriceHelper
import kotlin.math.roundToLong

object EnchantableItemPriceCalculator : PriceCalculator<EnchantableItem> {
    override val type = EnchantableItem::class.java

    private val enchantmentUpgradeItems = mapOf(
        KnownEnchantment.Scavenger to (MiscItemId.GoldenBounty to 6),
        KnownEnchantment.Pesterminator to (MiscItemId.ABeginnersGuideToPesthunting to 6),
        KnownEnchantment.Charm to (MiscItemId.ChainOfTheEndTimes to 6),
        KnownEnchantment.LuckOfTheSea to (MiscItemId.GoldBottleCap to 7),
        KnownEnchantment.Piscary to (MiscItemId.TroubledBubble to 7),
        KnownEnchantment.Frail to (MiscItemId.SeveredPincer to 7),
        KnownEnchantment.SpikedHook to (MiscItemId.OctopusTendril to 7)
    )

    private val blockedEnchantments = mapOf(
        WeaponItemId.Bonemerang to listOf(
            KnownEnchantment.Overload,
            KnownEnchantment.Power,
            KnownEnchantment.SoulEater
        ),
        WeaponItemId.DeathBow to listOf(KnownEnchantment.Overload, KnownEnchantment.Power, KnownEnchantment.SoulEater),
        ToolItemId.BasicGardeningAxe to listOf(KnownEnchantment.Replenish),
        ToolItemId.BasicGardeningHoe to listOf(KnownEnchantment.Replenish),
        ToolItemId.AdvancedGardeningAxe to listOf(KnownEnchantment.Replenish),
        ToolItemId.AdvancedGardeningHoe to listOf(KnownEnchantment.Replenish)
    )

    private val ignoredEnchantments = mapOf(
        KnownEnchantment.Scavenger to 5,
    )

    private val ignoreSilex = listOf(ToolItemId.PromisingShovel, ToolItemId.PromisingAxe)

    override fun calculatePrice(item: EnchantableItem): Long {
        val isEnchantedBook = item is EnchantedBook
        val isSingleEnchantedBook = isEnchantedBook && item.enchantments.size == 1

        var price = 0L

        for ((enchantment, level) in item.enchantments) {
            price += if (isEnchantedBook) {
                ((if (isSingleEnchantedBook) 1.0 else PriceHelper.getApplicationWorth(MiscItemId.EnchantedBook)) * (getEnchantedBookPrice(
                    enchantment,
                    level
                ))).roundToLong()
            } else {
                getEnchantmentPrice(item, enchantment, level)
            }
        }

        return price
    }

    fun getEnchantedBookPrice(enchantment: Enchantment, level: Int): Long {
        return PriceHelper.getBasePrice("ENCHANTMENT_${enchantment.apiName.uppercase()}_$level")
    }

    fun getEnchantmentPrice(item: EnchantableItem, enchantment: Enchantment, level: Int): Long {
        var price = 0L

        if (blockedEnchantments[item.id]?.contains(enchantment) == true) return 0
        if (ignoredEnchantments[enchantment] == level) return 0

        val level = if ((enchantment as? KnownEnchantment)?.stacking == true) 1 else level

        if (enchantment == KnownEnchantment.Efficiency && level >= 6 && !ignoreSilex.contains(item.id)) {
            val efficiencyLevel = level - (if (item.id == ToolItemId.Stonk) 6 else 5)

            if (efficiencyLevel > 0) {
                price += PriceHelper.getAppliedPrice(MiscItemId.Silex) * efficiencyLevel
            }
        }

        for ((upgradedEnchantment, pair) in enchantmentUpgradeItems) {
            if (upgradedEnchantment == enchantment && level >= pair.second) {
                price += PriceHelper.getAppliedPrice(pair.first)
            }
        }

        price += (PriceHelper.getBasePrice("ENCHANTMENT_${enchantment.apiName.uppercase()}_$level") * PriceHelper.getEnchantmentWorth(
            enchantment
        )).roundToLong()

        return price
    }
}