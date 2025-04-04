package net.dungeonhub.service

import net.dungeonhub.hypixel.entities.inventory.items.*
import net.dungeonhub.hypixel.entities.inventory.items.id.UnknownSkyblockItemId
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
            "rift_transferred",
            "giftbag_claim",
            "raffle_win",
            "raffle_year",
            "yearObtained"
        ),

        Abicase::class.java to listOf("model"),
        Accessory::class.java to listOf("talisman_enrichment"),
        Armor::class.java to listOf("artOfPeaceApplied", "color", "hecatomb_s_runs", "historic_dungeon_score"),
        ArmorOfDivan::class.java to listOf("gemstone_slots"),
        ArmorOfMagma::class.java to listOf("magmaCubesKilled"),
        ArmorOfTheRisingSun::class.java to listOf("leaderVotes", "leaderPosition"),
        Backpack::class.java to listOf("backpack_color"),
        BasketOfHopeMemento::class.java to listOf("basket_edition", "basket_player_name"),
        BingoCard::class.java to listOf("bingo_event", "playtime", "player"),
        BingosSecretMemento::class.java to listOf("goal_name", "bingo_event", "player_name", "player_uuid"),
        BlazeDagger::class.java to listOf("td_attune_mode"),
        BlazetekkHamRadio::class.java to listOf("blazetekk_channel"),
        BloodGodCrest::class.java to listOf("blood_god_kills"),
        BookOfProgression::class.java to listOf("upgradedRarity"),
        BottleOfJyrre::class.java to listOf("bottle_of_jyrre_seconds", "bottle_of_jyrre_last_update"),
        Bow::class.java to listOf("toxophilite_combat_xp"),
        BucketOfDye::class.java to listOf("dye_donated"),
        BuildersRuler::class.java to listOf("builder's_ruler_data"),
        BuildersWand::class.java to listOf("builder's_wand_data"),
        CakeSlice::class.java to listOf("century_year_obtained"),
        CakeSoul::class.java to listOf(
            "captured_player",
            "captured_date",
            "initiator_player",
            "cake_owner",
            "soul_durability"
        ),
        CapsaicinEyedropsOld::class.java to listOf("charges_used"),
        CenturyMemento::class.java to listOf("tickets", "winning_team", "recipient_team", "century_year"),
        CenturyPartyInvitation::class.java to listOf("levels_found"),
        CrownOfAvarice::class.java to listOf("collected_coins"),
        DarkAuctionItem::class.java to listOf("winning_bid"),
        DefuseKit::class.java to listOf("trapsDefused"),
        Deployable::class.java to listOf("jalapeno_count"),
        Drill::class.java to listOf(
            "drill_fuel",
            "drill_part_upgrade_module",
            "drill_part_engine",
            "drill_part_fuel_tank",
            "polarvoid"
        ),
        DrillPart::class.java to listOf("stored_drill_fuel"),
        DungeonItem::class.java to listOf("dungeon_item_level"),
        EditionItem::class.java to listOf("edition", "recipient_name", "recipient_id", "date", "sender_name"),
        EmptyThunderBottle::class.java to listOf("thunder_charge"),
        EnchantableItem::class.java to listOf("enchantments", "anvil_uses"),
        FarmingTool::class.java to listOf("farmed_cultivating", "mined_crops", "farming_for_dummies_count"),
        FelSword::class.java to listOf("sword_kills"),
        FinalDestinationArmor::class.java to listOf("eman_kills"),
        FishFood::class.java to listOf("pet_exp"),
        FishingTool::class.java to listOf("expertise_kills", "fishes_caught", "lava_creatures_killed"),
        FreeBoosterCookie::class.java to listOf("cookie_free_player_id"),
        FruitBowl::class.java to listOf("names_found", "players_clicked"),
        FungiCutter::class.java to listOf("fungi_cutter_mode"),
        Gear::class.java to listOf(
            "dungeon_item",
            "upgrade_level",
            "year",
            "dungeon_skill_req",
            "baseStatBoostPercentage",
            "item_tier"
        ),
        GearFromOphelia::class.java to listOf("shop_dungeon_floor_completion_required"),
        GemstoneGauntlet::class.java to listOf("gemstone_gauntlet_meter"),
        HerringTheFish::class.java to listOf("not_a_hint"),
        HurricaneBow::class.java to listOf("bow_kills"),
        ItemFromBoss::class.java to listOf("bossId", "spawnedFor"),
        ItemFromKuudra::class.java to listOf("boss_tier"),
        ItemWithAbility::class.java to listOf("power_ability_scroll"),
        ItemWithAttributes::class.java to listOf("attributes"),
        ItemWithCombatBooks::class.java to listOf("art_of_war_count", "stats_book"),
        ItemWithGems::class.java to listOf("gems"),
        ItemWithGreatSpookYear::class.java to listOf("year"),
        ItemWithHotPotatoBooks::class.java to listOf("hot_potato_count", "hotPotatoBonus"),
        ItemWithRune::class.java to listOf("runes"),
        JakeShopItem::class.java to listOf("entity_required"),
        JournalEntry::class.java to listOf("dungeon_paper_id"),
        KuudraTeethPlaque::class.java to listOf("kuudraCavityRarity"),
        LeaderboardItem::class.java to listOf(
            "leaderboard_score",
            "leaderboard_position",
            "leaderboard_player",
            "new_years_cake",
            "event"
        ),
        LoudmouthBass::class.java to listOf("bass_weight"),
        ManaDisintegratable::class.java to listOf("mana_disintegrator_count"),
        MelodysHair::class.java to listOf("tune"),
        MidasWeapon::class.java to listOf("gilded_gifted_coins", "additional_coins"),
        MiningToolItem::class.java to listOf("compact_blocks"),
        Minion::class.java to listOf(
            "resources_generated",
            "mithril_infusion",
            "free_will",
            "generator_tier",
            "total_generations"
        ),
        NecromancyItem::class.java to listOf("necromancer_souls"),
        NewYearCake::class.java to listOf("new_years_cake"),
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
            "personal_compact_11",
            "personal_compactor_0", /*ignore those, they are legacy data I guess*/
            "personal_compactor_1"
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
            "personal_deletor_11",
            "personal_compact_0", /*no idea why this exists*/
            "personal_compact_1",
            "personal_compact_2",
            "personal_compactor_0", /*ignore those, they are legacy data I guess*/
            "personal_compactor_1",
            "personal_compactor_2",
            "personal_compactor_3",
            "personal_compactor_4"
        ),
        PestVacuum::class.java to listOf("bookworm_books"),
        PetAsItem::class.java to listOf("petInfo"),
        Pickonimbus::class.java to listOf("pickonimbus_durability"),
        Postcard::class.java to listOf("post_card_minion_tier", "post_card_minion_type"),
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
        PowderCoatableItem::class.java to listOf("divan_powder_coating"),
        PrehistoricEgg::class.java to listOf(
            "blocks_walked",
            "rolled_4000",
            "rolled_10000",
            "rolled_20000",
            "rolled_40000"
        ),
        PromisingPickaxe::class.java to listOf("promising_pickaxe_breaks"),
        PromisingTool::class.java to listOf("blocksBroken"),
        PulseRing::class.java to listOf("thunder_charge"),
        RaffleTicket::class.java to listOf("raffle_uuid"),
        RaidersAxe::class.java to listOf("raider_kills"),
        RanchersBoots::class.java to listOf("ranchers_speed"),
        ReaperArmor::class.java to listOf("zombie_kills"),
        RecallPotion::class.java to listOf("recall_potion_biome", "recall_potion_location"),
        ReforgeableItem::class.java to listOf("modifier"),
        RepellingCandle::class.java to listOf("repelling_color", "rep_radius", "rep_particles"),
        Runebook::class.java to listOf("runic_kills"),
        Sack::class.java to listOf("sack_pss"),
        SaulsRecommendation::class.java to listOf("context"),
        SecretBingoCard::class.java to listOf("completion"),
        ShensAuctionItem::class.java to listOf("date", "price", "bid", "player", "auction"),
        ShinyItem::class.java to listOf("is_shiny"),
        SkinAppliable::class.java to listOf("dye_item", "skin"),
        SnowShooter::class.java to listOf("ammo"),
        Sprayonator::class.java to listOf("spray_item"),
        SqueakyMousemat::class.java to listOf("mousemat_yaw", "mousemat_pitch"),
        StaffOfTheRisingSun::class.java to listOf("leaderVotes", "leaderPosition"),
        Sword::class.java to listOf("champion_combat_xp", "wood_singularity_count"),
        TarantulaArmor::class.java to listOf("spider_kills"),
        TastyMithril::class.java to listOf("gourmand_uuid"),
        TeleportationSword::class.java to listOf("ethermerge", "tuned_transmission"),
        TimeBagItem::class.java to listOf("seconds_held"),
        TrainingWeights::class.java to listOf("trainingWeightsHeldTime", "maxed_stats"),
        TrapperCrest::class.java to listOf("pelts_earned"),
        TuningFork::class.java to listOf("tuning_fork_tuning"),
        VanquishedBlazeBelt::class.java to listOf("blaze_consumer"),
        VanquishedGhastCloak::class.java to listOf("ghast_blaster"),
        VanquishedGlowstoneGauntlet::class.java to listOf("glowing"),
        VanquishedMagmaNecklace::class.java to listOf("magma_cube_absorber"),
        Weapon::class.java to listOf("ultimateSoulEaterData"),
        WishingCompass::class.java to listOf("wishing_compass_uses"),
        WitherBlade::class.java to listOf("ability_scroll"),
        YogArmor::class.java to listOf("yogsKilled")
    )

    fun getMappedFields(item: SkyblockItem): List<String> {
        val result = mutableListOf<String>()

        for ((key, value) in itemFields) {
            if (key.isAssignableFrom(item.javaClass)) {
                result.addAll(value)
            }
        }

        return result
    }

    fun checkFields(item: SkyblockItem) {
        if (item.id is UnknownSkyblockItemId) return

        val fields = getMappedFields(item)

        val unmappedFields = item.extraAttributes.map { it.key }.filter { !fields.contains(it) }

        if (!unmappedFields.isEmpty()) {
            println(item.id.apiName + ": " + item.extraAttributes.filter { !fields.contains(it.key) })
        }

        assertTrue(
            unmappedFields.isEmpty(),
            "The item ${item.id.javaClass.simpleName}.${item.id} (${item.javaClass.simpleName}) doesn't have the following fields mapped, " +
                    "even tho it should have them: $unmappedFields"
        )
    }
}