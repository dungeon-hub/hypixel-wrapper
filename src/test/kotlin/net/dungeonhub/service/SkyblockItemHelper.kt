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
        ArmorOfGrowth::class.java to listOf("health"),
        ArmorOfMagma::class.java to listOf("magmaCubesKilled"),
        ArmorOfTheRisingSun::class.java to listOf("leaderVotes", "leaderPosition"),
        Backpack::class.java to listOf("backpack_color"),
        BasketOfHopeMemento::class.java to listOf("basket_edition", "basket_player_name"),
        BasketOfSeeds::class.java to listOf("basket_of_seeds_data"),
        BingoCard::class.java to listOf("bingo_event", "playtime", "player", "completion"),
        BingosSecretMemento::class.java to listOf("goal_name", "bingo_event", "player_name", "player_uuid"),
        BiomeStick::class.java to listOf("radius"),
        BlazeDagger::class.java to listOf("td_attune_mode"),
        BlazetekkHamRadio::class.java to listOf("blazetekk_channel"),
        BloodGodCrest::class.java to listOf("blood_god_kills"),
        BookOfProgression::class.java to listOf("upgradedRarity"),
        BottleOfJyrre::class.java to listOf("bottle_of_jyrre_seconds", "bottle_of_jyrre_last_update", "maxed_stats"),
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
        DiamondNecronHead::class.java to listOf("favorite_diamond_knight"),
        DianasBookshelf::class.java to listOf("chimera_found", "bookshelf_loops"),
        DittoBlob::class.java to listOf("ditto_og_item_id", "ditto_applied_skin", "ditto_applied_skin_signature"),
        DittoSkin::class.java to listOf("skinValue", "skinSignature"),
        DittoSkull::class.java to listOf("skullValue"),
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
        EditorsPencil::class.java to listOf("user", "sender"),
        EleanorArmor::class.java to listOf("coins_gained"),
        EmptyThunderBottle::class.java to listOf("thunder_charge"),
        EnchantableItem::class.java to listOf("enchantments", "anvil_uses"),
        FarmingTool::class.java to listOf("farmed_cultivating", "mined_crops", "farming_for_dummies_count"),
        FelSword::class.java to listOf("sword_kills"),
        FermentoHelmet::class.java to listOf("favorite_crop"),
        FinalDestinationArmor::class.java to listOf("eman_kills"),
        FishFood::class.java to listOf("pet_exp"),
        FishingRod::class.java to listOf("line", "hook", "sinker"),
        FishingTool::class.java to listOf("expertise_kills", "fishes_caught", "lava_creatures_killed"),
        FlexHelmet::class.java to listOf("fh_selected_skin", "fh_seconds_worn", "fh_seconds_worn_rift"),
        FreeBoosterCookie::class.java to listOf("cookie_free_player_id"),
        FruitBowl::class.java to listOf("names_found", "players_clicked"),
        FungiCutter::class.java to listOf("fungi_cutter_mode"),
        Gear::class.java to listOf(
            "dungeon_item",
            "upgrade_level",
            "year",
            "dungeon_skill_req",
            "baseStatBoostPercentage",
            "item_tier",
            "item_durability"
        ),
        GearFromOphelia::class.java to listOf("shop_dungeon_floor_completion_required"),
        GemstoneGauntlet::class.java to listOf("gemstone_gauntlet_meter"),
        GodPotion::class.java to listOf("mixins"),
        GreaterBackpack::class.java to listOf("greater_backpack_data"),
        HelmetOfDivan::class.java to listOf("favorite_gemstone"),
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
        JumboBackpack::class.java to listOf("jumbo_backpack_data"),
        KuudraTeethPlaque::class.java to listOf("kuudraCavityRarity"),
        LargeBackpack::class.java to listOf("large_backpack_data"),
        LeaderboardItem::class.java to listOf(
            "leaderboard_score",
            "leaderboard_position",
            "leaderboard_player",
            "new_years_cake",
            "event"
        ),
        LoudmouthBass::class.java to listOf("bass_weight"),
        MagicalMap::class.java to listOf("dontUpdateStack", "dontSaveToProfile"),
        MagmaLordHelmet::class.java to listOf("favorite_shark"),
        ManaDisintegratable::class.java to listOf("mana_disintegrator_count"),
        MediumBackpack::class.java to listOf("medium_backpack_data"),
        MelodysHair::class.java to listOf("tune"),
        MenderHelmet::class.java to listOf("favorite_snake"),
        MidasWeapon::class.java to listOf("gilded_gifted_coins", "additional_coins"),
        MiningToolItem::class.java to listOf("compact_blocks"),
        Minion::class.java to listOf(
            "resources_generated",
            "mithril_infusion",
            "free_will",
            "generator_tier",
            "total_generations"
        ),
        NameTag::class.java to listOf("name_tag"),
        NecromancyItem::class.java to listOf("necromancer_souls"),
        NecronsLadder::class.java to listOf("handles_found", "handle_loops"),
        NetherWartPouch::class.java to listOf("nether_wart_pouch_data", "netherwart_pouch_data"),
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
        PuzzleCube::class.java to listOf("puzzles_solved"),
        RaffleTicket::class.java to listOf("raffle_uuid"),
        RaidersAxe::class.java to listOf("raider_kills"),
        RanchersBoots::class.java to listOf("ranchers_speed"),
        ReaperArmor::class.java to listOf("zombie_kills"),
        RecallPotion::class.java to listOf("recall_potion_biome", "recall_potion_location"),
        RecluseFang::class.java to listOf("spider_kills"),
        ReforgeableItem::class.java to listOf("modifier"),
        RefundBoosterCookie::class.java to listOf("cookie_free_player_id"),
        RepellingCandle::class.java to listOf("repelling_color", "rep_radius", "rep_particles"),
        Runebook::class.java to listOf("runic_kills"),
        Sack::class.java to listOf("sack_pss"),
        SaulsRecommendation::class.java to listOf("context"),
        ShensAuctionItem::class.java to listOf("date", "price", "bid", "player", "auction"),
        ShinyItem::class.java to listOf("is_shiny"),
        SkeletorArmor::class.java to listOf("skeletorKills"),
        SkinAppliable::class.java to listOf("dye_item", "skin"),
        SmallBackpack::class.java to listOf("small_backpack_data"),
        SnowShooter::class.java to listOf("ammo"),
        SprayCan::class.java to listOf(
            "spray",
            "orange",
            "red",
            "magenta",
            "pink",
            "white",
            "yellow",
            "light_blue",
            "silver",
            "green",
            "lime",
            "black",
            "brown",
            "blue",
            "purple",
            "cyan",
            "gray",
            "orientation"
        ),
        Sprayonator::class.java to listOf("spray_item"),
        SqueakyMousemat::class.java to listOf("mousemat_yaw", "mousemat_pitch"),
        StaffOfTheRisingSun::class.java to listOf("leaderVotes", "leaderPosition"),
        Sword::class.java to listOf("champion_combat_xp", "wood_singularity_count"),
        SynthesizerV1::class.java to listOf("EXE"),
        SynthesizerV2::class.java to listOf("WAI"),
        SynthesizerV3::class.java to listOf("ZEE"),
        TarantulaArmor::class.java to listOf("spider_kills"),
        TastyMithril::class.java to listOf("gourmand_uuid"),
        TeleportationSword::class.java to listOf("ethermerge", "tuned_transmission"),
        TimeBagItem::class.java to listOf("seconds_held", "lastForceEvolvedTime"),
        TrainingWeights::class.java to listOf("trainingWeightsHeldTime", "maxed_stats"),
        TrapperCrest::class.java to listOf("pelts_earned"),
        TuningFork::class.java to listOf("tuning_fork_tuning"),
        VanquishedBlazeBelt::class.java to listOf("blaze_consumer"),
        VanquishedGhastCloak::class.java to listOf("ghast_blaster"),
        VanquishedGlowstoneGauntlet::class.java to listOf("glowing"),
        VanquishedMagmaNecklace::class.java to listOf("magma_cube_absorber"),
        WardenHelmet::class.java to listOf("favorite_sentinel_warden"),
        Weapon::class.java to listOf("ultimateSoulEaterData"),
        WikiJournal::class.java to listOf("user", "sender", "wikiName"),
        WishingCompass::class.java to listOf("wishing_compass_uses"),
        WitherBlade::class.java to listOf("ability_scroll"),
        WitherGoggles::class.java to listOf("favorite_evil_skin"),
        WizardWand::class.java to listOf("intelligence_earned"),
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

        assertTrue(
            unmappedFields.isEmpty(),
            "The item ${item.id.javaClass.simpleName}.${item.id} (${item.javaClass.simpleName}) doesn't have the following fields mapped, " +
                    "even tho it should have them: $unmappedFields"
        )
    }
}