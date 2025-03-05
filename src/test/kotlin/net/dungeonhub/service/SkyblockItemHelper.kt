package net.dungeonhub.service

import net.dungeonhub.hypixel.entities.inventory.items.*
import net.dungeonhub.hypixel.entities.inventory.items.special.HurricaneBow
import kotlin.test.assertTrue

object SkyblockItemHelper {
    val itemFields: Map<Class<out SkyblockItemFactory>, List<String>> = mapOf(
        SkyblockItem::class.java to listOf(
            "id",
            "uuid",
            "originTag",
            "uuid",
            "timestamp",
            "rarity_upgrades",
            "donated_museum",
            "soulbound",
            "rift_transferred"
        ),
        Accessory::class.java to listOf("talisman_enrichment"),
        Armor::class.java to listOf("artOfPeaceApplied"),
        Bow::class.java to listOf("toxophilite_combat_xp"),
        Deployable::class.java to listOf("jalapeno_count"),
        EnchantableItem::class.java to listOf("enchantments", "anvil_uses"),
        Gear::class.java to listOf("attributes", "runes", "dungeon_item", "upgrade_level", "year"),
        ItemFromBoss::class.java to listOf("bossId", "spawnedFor"),
        ItemFromKuudra::class.java to listOf("boss_tier"),
        ItemWithAbility::class.java to listOf("power_ability_scroll"),
        ItemWithGems::class.java to listOf("gems"),
        ItemWithHotPotatoBooks::class.java to listOf("hot_potato_count"),
        ManaDisintegratable::class.java to listOf("mana_disintegrator_count"),
        PetAsItem::class.java to listOf("petInfo", "year"),
        ReforgeableItem::class.java to listOf("modifier"),
        ShinyItem::class.java to listOf("is_shiny"),
        SkinAppliable::class.java to listOf("dye_item", "skin"),
        TeleportationSword::class.java to listOf("ethermerge", "tuned_transmission"),
        Weapon::class.java to listOf("art_of_war_count"),
        HurricaneBow::class.java to listOf("bow_kills")
    )

    fun getFields(item: SkyblockItem): List<String> {
        val result = mutableListOf<String>()

        for ((key, value) in itemFields) {
            if (key.isAssignableFrom(item.javaClass)) {
                result.addAll(value)
            }
        }

        return result
    }

    fun checkFields(item: SkyblockItem) {
        val fields = getFields(item)

        val unmappedFields = item.extraAttributes.map { it.key }.filter { !fields.contains(it) }

        assertTrue(
            unmappedFields.isEmpty(),
            "The item ${item.id} (${item.javaClass.simpleName}) doesn't have the following fields mapped, " +
                    "even tho it should have them: $unmappedFields"
        )
    }
}