package net.dungeonhub.hypixel.entities.skyblock.price

import net.dungeonhub.hypixel.entities.inventory.items.Enchantment
import net.dungeonhub.hypixel.entities.inventory.items.KnownEnchantment
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem
import net.dungeonhub.hypixel.entities.inventory.items.id.*
import net.dungeonhub.hypixel.entities.skyblock.price.calculator.BasePriceCalculator
import kotlin.math.roundToLong

object PriceHelper {
    //TODO needed?: essence: 0.75
    //TODO gemstoneSlots: 0.6
    //TODO helmet skins: 0.8
    private val applicationWorth = mapOf<List<KnownSkyblockItemId>, Double>(
        KnownEnrichmentId.entries to 0.5,
        listOf(MiscItemId.FarmingForDummies) to 0.5,
        KnownAbilityScrollId.entries to 0.5,
        listOf(MiscItemId.WoodSingularity) to 0.5,
        listOf(MiscItemId.TheArtOfWar) to 0.6,
        listOf(DungeonItemId.FumingPotatoBook) to 0.6,
        listOf(CosmeticItemId.Rune, CosmeticItemId.UniqueRune) to 0.6,
        listOf(MiscItemId.TransmissionTuner) to 0.7,
        listOf(MiscItemId.PocketSackInASack) to 0.7,
        listOf(MiscItemId.Silex) to 0.75,
        listOf(
            MiscItemId.GoldenBounty,
            MiscItemId.ABeginnersGuideToPesthunting,
            MiscItemId.ChainOfTheEndTimes,
            MiscItemId.GoldBottleCap,
            MiscItemId.TroubledBubble,
            MiscItemId.SeveredPincer,
            MiscItemId.OctopusTendril
        ) to 0.8,
        listOf(MiscItemId.TheArtOfPeace) to 0.8,
        listOf(ForgeableItemId.DivansPowderCoating) to 0.8,
        listOf(MiscItemId.JalapenoBook) to 0.8,
        listOf(MiscItemId.ManaDisintegrator) to 0.8,
        listOf(DungeonItemId.Recombobulator3000) to 0.8,
        listOf(MiscItemId.ThunderInABottle) to 0.8, //TODO check if this is needed
        listOf(MiscItemId.EnchantedBook) to 0.85,
        KnownDyeId.entries to 0.9,
        listOf(ForgeableItemId.GemstoneChamber) to 0.9,
        listOf(MiscItemId.AttributeShard) to 1.0,
        KnownRodPartId.entries to 1.0,
        DrillPartId.entries to 1.0,
        listOf(MiscItemId.EtherwarpConduit, MiscItemId.EtherwarpMerger) to 1.0,
        listOf(
            DungeonItemId.FirstMasterStar,
            DungeonItemId.SecondMasterStar,
            DungeonItemId.ThirdMasterStar,
            DungeonItemId.FourthMasterStar,
            DungeonItemId.FifthMasterStar
        ) to 1.0,
        GemstoneItemId.entries to 1.0,
        listOf(MiscItemId.HotPotatoBook) to 1.0,
        KnownNecronsScroll.entries to 1.0,
        listOf(RiftItemId.PolarvoidBook) to 1.0,
        ReforgeStoneId.entries to 1.0,
        listOf(
            MiscItemId.SimpleCarrotCandy,
            MiscItemId.GreatCarrotCandy,
            MiscItemId.SuperbCarrotCandy,
            MiscItemId.UltimateCarrotCandy
        ) to 0.65,
        KnownPetItem.entries to 1.0,
        KnownPetSkinId.entries to 0.8
    )

    private val enchantmentWorth = mapOf(
        KnownEnchantment.CounterStrike to 0.2,
        KnownEnchantment.BigBrain to 0.35,
        KnownEnchantment.Inferno to 0.35,
        KnownEnchantment.Overload to 0.35,
        KnownEnchantment.SoulEater to 0.35,
        KnownEnchantment.FatalTempo to 0.65
    )

    fun getPrice(skyblockItem: SkyblockItem): Long {
        var price = getBasePrice(skyblockItem.uniqueName).takeIf { it != 0L } ?: getBasePrice(skyblockItem.id)

        for (calculator in BasePriceCalculator.calculators) {
            if (calculator.appliesToItem(skyblockItem)) {
                price += calculator.calculateBasePrice(skyblockItem)
            }
        }

        return price
    }

    fun getBasePrice(uniqueName: String): Long {
        //TODO implement -> SkyHelper GitHub?
        return 0
    }

    fun getBasePrice(skyblockItemId: SkyblockItemId): Long {
        return getBasePrice(skyblockItemId.apiName)
    }

    fun getAppliedPrice(skyblockItemId: SkyblockItemId): Long {
        return (getBasePrice(skyblockItemId) * getApplicationWorth(skyblockItemId)).roundToLong()
    }

    fun getApplicationWorth(skyblockItemId: SkyblockItemId): Double {
        for (entry in applicationWorth) {
            if (entry.key.contains(skyblockItemId)) {
                return entry.value
            }
        }

        return 1.0
    }

    fun getEnchantmentWorth(enchantment: Enchantment): Double {
        return enchantmentWorth[enchantment] ?: getApplicationWorth(MiscItemId.EnchantedBook)
    }
}