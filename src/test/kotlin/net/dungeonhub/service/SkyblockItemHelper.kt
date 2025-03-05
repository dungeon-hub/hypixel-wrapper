package net.dungeonhub.service

import net.dungeonhub.hypixel.entities.inventory.items.*
import net.dungeonhub.hypixel.entities.inventory.items.special.*
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
        Abicase::class.java to listOf("model"),
        BloodGodCrest::class.java to listOf("blood_god_kills"),
        BookOfProgression::class.java to listOf("upgradedRarity"),
        BucketOfDye::class.java to listOf("dye_donated"),
        BuildersRuler::class.java to listOf("builder's_ruler_data"),
        BuildersWand::class.java to listOf("builder's_wand_data"),
        GreatSpookAccessory::class.java to listOf("year", "edition"),
        HegemonyArtifact::class.java to listOf("winning_bid"),
        HurricaneBow::class.java to listOf("bow_kills"),
        MelodysHair::class.java to listOf("tune"),
        NewYearCakeBag::class.java to listOf("new_year_cake_bag_data"),
        PandorasBox::class.java to listOf("pandora-rarity"),
        PartyHat::class.java to listOf("party_hat_year", "party_hat_color", "party_hat_emoji"),
        PersonalCompactor::class.java to listOf(
            "PERSONAL_DELETOR_ACTIVE",
            "personal_compact_0",
            "personal_compact_1",
            "personal_compact_2",
            "personal_compact_3",
            "personal_compact_4",
            "personal_compact_5",
            "personal_compact_6",
            "personal_compact_7",
            "personal_compact_8",
            "personal_compact_9",
            "personal_compact_10",
            "personal_compact_11"
        ),
        PersonalDeletor::class.java to listOf(
            "PERSONAL_DELETOR_ACTIVE",
            "personal_deletor_0",
            "personal_deletor_1",
            "personal_deletor_2",
            "personal_deletor_3",
            "personal_deletor_4",
            "personal_deletor_5",
            "personal_deletor_6",
            "personal_deletor_7",
            "personal_deletor_8",
            "personal_deletor_9",
            "personal_deletor_10",
            "personal_deletor_11"
        ),
        Potion::class.java to listOf(
            "potion_level",
            "potion",
            "potion_type",
            "splash",
            "effects",
            "potion_name",
            "dungeon_potion",
            "shop_dungeon_floor_completion_required",
            "enhanced",
            "extended",
            "last_potion_ingredient",
            "should_give_alchemy_exp"
        ),
        PulseRing::class.java to listOf("thunder_charge"),
        RaidersAxe::class.java to listOf("raider_kills"),
        Runebook::class.java to listOf("runic_kills"),
        StaffOfTheRisingSun::class.java to listOf("leaderVotes", "leaderPosition"),
        TrapperCrest::class.java to listOf("pelts_earned"),
        WitherBlade::class.java to listOf("ability_scroll")
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