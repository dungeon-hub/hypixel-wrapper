package net.dungeonhub

import com.google.gson.JsonArray
import net.dungeonhub.hypixel.client.MemoryCacheApiClient
import net.dungeonhub.hypixel.client.RestApiClient
import net.dungeonhub.hypixel.connection.HypixelApiConnection
import net.dungeonhub.hypixel.entities.inventory.GemstoneQuality
import net.dungeonhub.hypixel.entities.inventory.ItemStack
import net.dungeonhub.hypixel.entities.inventory.items.*
import net.dungeonhub.hypixel.entities.skyblock.*
import net.dungeonhub.hypixel.entities.skyblock.currencies.KnownCurrencyTypes
import net.dungeonhub.hypixel.entities.skyblock.currencies.KnownEssenceType
import net.dungeonhub.hypixel.entities.skyblock.dungeon.KnownDungeonType
import net.dungeonhub.hypixel.entities.skyblock.misc.ProfileGameMode
import net.dungeonhub.hypixel.entities.skyblock.misc.fromSkyblockTime
import net.dungeonhub.hypixel.entities.skyblock.pet.KnownPetItem
import net.dungeonhub.hypixel.entities.skyblock.slayer.KnownSlayerType
import net.dungeonhub.provider.GsonProvider
import net.dungeonhub.service.TestHelper
import net.dungeonhub.strategy.ApiClientStrategy
import org.junit.jupiter.api.assertDoesNotThrow
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.spy
import org.mockito.kotlin.whenever
import java.nio.file.Files
import java.nio.file.Paths
import java.time.Instant
import java.util.*
import kotlin.io.path.name
import kotlin.test.*

class TestSkyblockProfile {
    @Test
    fun testProfileNames() {
        val profileNames = mapOf(
            "c028e7cc-708f-4f53-b835-9c681c61cd1c" to "Pomegranate",
            "529bddd9-3d7a-4277-9fe7-4e6aae86813d" to "Blueberry"
        )

        val exampleProfile = TestHelper.readFile("example_skyblock_profiles.json")

        val profiles = GsonProvider.gson.fromJson(exampleProfile, JsonArray::class.java).asList()

        for (profileJson in profiles) {
            val profile = profileJson.toSkyblockProfile()

            assertNotNull(profile)

            assertEquals(profile.cuteName, profileNames[profile.profileId.toString()])
        }

        assertEquals(profiles.size, 3)
    }

    @Test
    fun testSkyblockTimeConversion() {
        assertEquals(Instant.ofEpochSecond(1560275700L + 174463288), 174463288L.fromSkyblockTime())
    }

    @Test
    fun testProfileGameModes() {
        val fullProfilesJson = TestHelper.readFile("full-profiles/full_skyblock_profiles.json")

        val fullProfiles = GsonProvider.gson.fromJson(fullProfilesJson, JsonArray::class.java).asList()

        val parsedProfiles = fullProfiles.map { it.toSkyblockProfile() }
        assertEquals(4, parsedProfiles.size)
        assertEquals(3, parsedProfiles.filter { it.gameMode == ProfileGameMode.Default }.size)
        assertEquals(0, parsedProfiles.filter { it.gameMode == ProfileGameMode.Ironman }.size)
        assertEquals(1, parsedProfiles.filter { it.gameMode == ProfileGameMode.Bingo }.size)
    }

    @Test
    fun testFullProfile() {
        val profilesDirectory = javaClass.classLoader.getResource("full-profiles/")!!.toURI()

        for (file in Files.list(Paths.get(profilesDirectory))) {
            val fullProfilesJson = TestHelper.readFile("full-profiles/${file.name}")

            val fullProfiles = GsonProvider.gson.fromJson(fullProfilesJson, JsonArray::class.java).asList()

            for (fullProfileJson in fullProfiles) {
                val fullProfile = fullProfileJson.toSkyblockProfile()

                assertNotNull(fullProfile)

                assertNotNull(fullProfile.cuteName)
            }
        }
    }

    @Test
    fun testMemberType() {
        val currentMembers = mapOf(
            "108e00ad-2477-4ebf-9b26-c6a4a08b0f78" to 3,
            "8f91bea5-89db-4e25-82ea-a0493de3e118" to 1,
            "529bddd9-3d7a-4277-9fe7-4e6aae86813d" to 3,
            "c028e7cc-708f-4f53-b835-9c681c61cd1c" to 3
        )

        val pastMembers = mapOf(
            "108e00ad-2477-4ebf-9b26-c6a4a08b0f78" to 6,
            "8f91bea5-89db-4e25-82ea-a0493de3e118" to 0,
            "529bddd9-3d7a-4277-9fe7-4e6aae86813d" to 10,
            "c028e7cc-708f-4f53-b835-9c681c61cd1c" to 4
        )

        val pendingMembers = mapOf(
            "108e00ad-2477-4ebf-9b26-c6a4a08b0f78" to 0,
            "8f91bea5-89db-4e25-82ea-a0493de3e118" to 0,
            "529bddd9-3d7a-4277-9fe7-4e6aae86813d" to 1,
            "c028e7cc-708f-4f53-b835-9c681c61cd1c" to 0
        )

        val fullProfilesJson = TestHelper.readFile("full-profiles/full_skyblock_profiles.json")

        val fullProfiles = GsonProvider.gson.fromJson(fullProfilesJson, JsonArray::class.java).asList()

        for (fullProfileJson in fullProfiles) {
            val fullProfile = fullProfileJson.toSkyblockProfile()

            assertEquals(currentMembers[fullProfile.profileId.toString()], fullProfile.currentMembers.size)
            assertEquals(
                pastMembers[fullProfile.profileId.toString()],
                fullProfile.members.filterIsInstance<PastMember>().size
            )
            assertEquals(
                pendingMembers[fullProfile.profileId.toString()],
                fullProfile.members.filterIsInstance<PendingMember>().size
            )
        }
    }

    @Test
    fun testSkillLevels() {
        //only check blueberry profile, as there the skill levels are known
        val profileToCheck = UUID.fromString("529bddd9-3d7a-4277-9fe7-4e6aae86813d")

        val memberToCheck = UUID.fromString("39642ffc-a7fb-4d24-a1d4-916f4cad1d98")

        val levels = mapOf(
            KnownSkill.Alchemy to 50,
            KnownSkill.Carpentry to 50,
            KnownSkill.Combat to 60,
            KnownSkill.Enchanting to 60,
            KnownSkill.Farming to 60,
            KnownSkill.Fishing to 50,
            KnownSkill.Foraging to 43,
            KnownSkill.Mining to 60,
            KnownSkill.Runecrafting to 25,
            KnownSkill.Taming to 60,
            KnownSkill.Social to 16
        )

        val fullProfilesJson = TestHelper.readFile("full-profiles/full_skyblock_profiles.json")

        val fullProfiles = GsonProvider.gson.fromJson(fullProfilesJson, JsonArray::class.java).asList()

        var checkHappened = false

        for (fullProfileJson in fullProfiles) {
            val fullProfile = fullProfileJson.toSkyblockProfile()

            if (fullProfile.profileId == profileToCheck) {
                for (member in fullProfile.members) {
                    if (member.uuid == memberToCheck) {
                        checkHappened = true

                        assertTrue(member.playerData != null && member.playerData!!.experience != null)
                        assertTrue(member is CurrentMember)
                        assertNotNull(member.dungeons?.classAverage)

                        for (skillExperience in member.playerData.experience!!.entries) {
                            assertTrue(skillExperience.key is KnownSkill)

                            val skill = skillExperience.key as KnownSkill

                            val level = skill.calculateLevel(skillExperience.value)

                            assertEquals(levels[skill], level)
                        }

                        assertEquals(54.78, member.playerData.skillAverage)

                        assertEquals(43, member.dungeons?.catacombsLevel)
                        assertEquals(37.0, member.dungeons?.classAverage)
                    }
                }
            }
        }

        assertTrue(checkHappened)
    }

    @Test
    fun testMagicalPower() {
        val memberToCheck = UUID.fromString("39642ffc-a7fb-4d24-a1d4-916f4cad1d98")

        val fullProfilesJson = TestHelper.readFile("full-profiles/full_skyblock_profiles.json")

        val fullProfiles = GsonProvider.gson.fromJson(fullProfilesJson, JsonArray::class.java).asList()

        val highestMagicalPower = fullProfiles.map { it.toSkyblockProfile() }
            .maxOf { it.getCurrentMember(memberToCheck)!!.accessoryBag?.highestMagicalPower ?: 0 }

        assertEquals(1469, highestMagicalPower)
    }

    @Test
    fun testFairySouls() {
        val profilesDirectory = javaClass.classLoader.getResource("full-profiles/")!!.toURI()

        for (file in Files.list(Paths.get(profilesDirectory))) {
            val fullProfilesJson = TestHelper.readFile("full-profiles/${file.name}")

            val fullProfiles = GsonProvider.gson.fromJson(fullProfilesJson, JsonArray::class.java).asList()

            for (fullProfileJson in fullProfiles) {
                val fullProfile = fullProfileJson.toSkyblockProfile()

                fullProfile.members.filterIsInstance<CurrentMember>().forEach { member ->
                    if (member.fairySoulData != null) {
                        assertEquals(
                            member.fairySoulData.unspentSouls,
                            member.fairySoulData.totalCollected - member.fairySoulData.totalExchanged,
                            "Profile member ${member.uuid} on profile ${fullProfile.cuteName} (${fullProfile.profileId}) has an invalid count of fairy souls."
                        )
                    }
                }
            }
        }
    }

    @Test
    fun testNbtParsing() {
        val profileToCheck = UUID.fromString("529bddd9-3d7a-4277-9fe7-4e6aae86813d")

        val memberToCheck = UUID.fromString("39642ffc-a7fb-4d24-a1d4-916f4cad1d98")

        val fullProfilesJson = TestHelper.readFile("full-profiles/full_skyblock_profiles.json")

        val fullProfiles =
            GsonProvider.gson.fromJson(fullProfilesJson, JsonArray::class.java).asList().map { it.toSkyblockProfile() }

        val member = fullProfiles.first { it.profileId == profileToCheck }.getCurrentMember(memberToCheck)

        assertNotNull(member)
        assertNotNull(member.inventory)
        assertNotNull(member.inventory?.enderChestContent)
        assertNotNull(member.inventory?.armor)
        assertNotNull(member.inventory?.equipment)
        assertNotNull(member.inventory?.personalVault)
        assertNotNull(member.inventory?.equippedWardrobeSlot)
        assertNotNull(member.inventory?.wardrobeContents)

        assertEquals(
            "Heroic Hyperion ✪✪✪✪✪➌",
            member.inventory?.inventoryContents!!.items.filterNotNull().first().rawName
        )
        assertEquals("Abiphone XII Mega", member.inventory?.inventoryContents?.items?.filterNotNull()?.last()?.rawName)
    }

    @Test
    fun testItemDataParsing() {
        assertTrue(KnownEnchantment.TheOne.isUltimate())

        for (fullProfiles in TestHelper.readAllSkyblockProfiles()) {
            for (fullProfile in fullProfiles) {
                fullProfile.members.filterIsInstance<CurrentMember>().forEach { member ->
                    if (member.inventory != null) {
                        val inventory = member.inventory

                        checkItems(inventory?.inventoryContents?.items?.filterNotNull() ?: emptyList())
                        checkItems(inventory?.enderChestContent?.items?.filterNotNull() ?: emptyList())
                        inventory?.backpackIcons?.values?.forEach { checkItems(it.items.filterNotNull()) }
                        inventory?.bagContents?.values?.forEach { checkItems(it.items.filterNotNull()) }
                        checkItems(inventory?.armor?.items?.filterNotNull() ?: emptyList())
                        checkItems(inventory?.equipment?.items?.filterNotNull() ?: emptyList())
                        checkItems(inventory?.personalVault?.items?.filterNotNull() ?: emptyList())
                        inventory?.backpackContents?.values?.forEach { checkItems(it.items.filterNotNull()) }
                        checkItems(inventory?.wardrobeContents?.items?.filterNotNull() ?: emptyList())
                    }
                }
            }
        }

        for (museum in TestHelper.readAllMuseumData()) {
            for (museumData in museum.museumData.values) {
                checkItems(museumData.allItems)
            }
        }
    }

    fun checkItems(items: List<ItemStack>) {
        for (item in items) {
            assertNotNull(item.tag)
            assertNotNull(item.name)
            assertNotNull(item.rawName)
            assertNotNull(item.extraAttributes)

            if (item is SkyblockItem) {
                assertNotNull(item.id)
                if (item.id is KnownSkyblockItemId.UnknownSkyblockItemId) {
                    //println(item.rawName + "(" + item.id.apiName + ")")
                }
                //TODO enable once fully mapped
                //assertIsNot<KnownSkyblockItemId.UnknownSkyblockItemId>(item.id)
                assertTrue(item.runes.isEmpty() || item.runes.size == 1)
                assertTrue(item.gems == null || item.gems!!.appliedGemstones.values.all {
                    GemstoneQuality.entries.contains(
                        it.gemstoneQuality
                    )
                })

                if (item is EnchantableItem) {
                    assertNotNull(item.enchantments)
                    item.enchantments.forEach {
                        assertIsNot<KnownEnchantment.UnknownEnchantment>(
                            it.key,
                            "Item ${item.id.apiName} has enchantments (${it.key.apiName}), but the wrapper doesn't acknowledge that!"
                        )
                    }
                } else {
                    //TODO reenable once everything is mapped
                    /*assertTrue("Item ${item.id.apiName} has enchantments, but the wrapper doesn't acknowledge that!") {
                        !item.extraAttributes.contains(
                            "enchantments"
                        )
                    }*/
                }

                if (item is WitherBlade) {
                    assertNotNull(item.abilityScrolls)
                } else {
                    assertTrue { !item.extraAttributes.contains("ability_scroll") }
                }

                if (item is Gear) {
                    assertNotNull(item.attributes)
                } else {
                    //TODO reenable once everything is mapped
                    /*assertTrue("Item ${item.id.apiName} has attributes, but the wrapper doesn't acknowledge that!") {
                        !item.extraAttributes.contains(
                            "attributes"
                        )
                    }*/
                }
                assertNotNull(item.newYearCakeBagData)
                assertDoesNotThrow { item.dungeonSkillRequirement }

                if (item is BuildersWand) {
                    assertNotNull(item.buildersWandData)
                }

                if(item is BuildersRuler) {
                    assertNotNull(item.buildersRulerData)
                }
            }
        }
    }

    @Test
    fun checkSkyblockMenuPresence() {
        val profilesDirectory = javaClass.classLoader.getResource("full-profiles/")!!.toURI()

        for (file in Files.list(Paths.get(profilesDirectory))) {
            val fullProfilesJson = TestHelper.readFile("full-profiles/${file.name}")

            val fullProfiles = GsonProvider.gson.fromJson(fullProfilesJson, JsonArray::class.java).asList()

            for (fullProfileJson in fullProfiles) {
                val fullProfile = fullProfileJson.toSkyblockProfile()

                fullProfile.members.filterIsInstance<CurrentMember>().forEach { member ->
                    if (member.inventory != null && member.inventory?.inventoryContents != null) {
                        val inventoryContent = member.inventory?.inventoryContents!!.items

                        val skyblockMenu = inventoryContent[8]

                        if (skyblockMenu != null) {
                            //apparently some profiles still exist with this outdated item name :/
                            if (skyblockMenu.name == "§aSkyBlock Menu §7(Right Click)") {
                                assertEquals("§aSkyBlock Menu §7(Right Click)", skyblockMenu.name)
                                assertEquals("SkyBlock Menu (Right Click)", skyblockMenu.rawName)
                            } else {
                                assertEquals("§aSkyBlock Menu §7(Click)", skyblockMenu.name)
                                assertEquals("SkyBlock Menu (Click)", skyblockMenu.rawName)
                            }
                        }
                    }
                }
            }
        }
    }

    @Test
    fun testNullResponse() {
        val uuid = UUID.fromString("4f326809-7578-47e3-bd75-a21b9470d333")

        assertNull(nullResponseClient.fetchSkyblockProfiles(uuid))

        val profiles = nullResponseClient.getSkyblockProfiles(uuid)

        assertNotNull(profiles)
        assertEquals(uuid, profiles.owner)
        assertEquals(0, profiles.profiles.size)
    }

    @Test
    fun testAllItemIds() {
        TestHelper.readItemList().forEach {
            val apiName = it.getAsJsonPrimitive("id").asString

            val id = KnownSkyblockItemId.fromApiName(apiName)

            // TODO enable once mapped
            /*assertIsNot<KnownSkyblockItemId.UnknownSkyblockItemId>(
                id,
                "Item $apiName has not been added to Skyblock item ID list",
            )*/
        }
    }

    @Test
    fun testNoInvalidDataTypes() {
        val duplicateSkyblockItemIds = HashSet<String>()
        val uniqueSkyblockItemIds = HashSet<String>()

        for (item in KnownSkyblockItemId.entries.map { it.apiName }) {
            if (!uniqueSkyblockItemIds.add(item)) {
                duplicateSkyblockItemIds.add(item)
            }
        }

        assertTrue("There are some skyblock item ids that were used multiple times: $duplicateSkyblockItemIds") {
            duplicateSkyblockItemIds.isEmpty() && uniqueSkyblockItemIds.size == KnownSkyblockItemId.entries.size
        }

        val nonExistentItems = listOf(
            // These are unobtainable :(
            KnownSkyblockItemId.AdminLavaRod,
            KnownSkyblockItemId.AdminsWarmSweater,
            KnownSkyblockItemId.BootsOfTheStars,
            KnownSkyblockItemId.BowOfTheUniverse,
            KnownSkyblockItemId.EnchantedClockAdmin,
            KnownSkyblockItemId.EternalCrystal,
            KnownSkyblockItemId.FerocitySword150,
            KnownSkyblockItemId.FerocitySword50,
            KnownSkyblockItemId.GhastHead,
            KnownSkyblockItemId.GiantPileOfCash,
            KnownSkyblockItemId.HelmetOfTheStars,
            KnownSkyblockItemId.Kindred,
            KnownSkyblockItemId.PotatoCrown,
            KnownSkyblockItemId.ShimmersparkleChestplate,
            KnownSkyblockItemId.ShinyRelic,
            KnownSkyblockItemId.SwordOfTheMultiverse,
            KnownSkyblockItemId.SwordOfTheStars9000,
            KnownSkyblockItemId.SwordOfTheUniverse,
            KnownSkyblockItemId.Voodoo,
            KnownSkyblockItemId.RemoteTimeChamberRemote,
            KnownSkyblockItemId.HellstormWand,
            KnownSkyblockItemId.MinecartWithCommandBlock,
            KnownSkyblockItemId.SwordOfLiveReloading,
            KnownSkyblockItemId.HelmetOfLiveReloading,
            KnownSkyblockItemId.GiantsEyeSword,
            KnownSkyblockItemId.KuudraWashingMachine,
            KnownSkyblockItemId.DragonEgg,

            // These are quite impossible to find
            KnownSkyblockItemId.BurningHollowHelmet,
            KnownSkyblockItemId.DittoSkin,
            KnownSkyblockItemId.FieryFervorBoots,
            KnownSkyblockItemId.FieryFervorChestplate,
            KnownSkyblockItemId.FieryFervorHelmet,
            KnownSkyblockItemId.FieryFervorLeggings,
            KnownSkyblockItemId.FieryHollowHelmet,
            KnownSkyblockItemId.GoldenDanteStatue,
            KnownSkyblockItemId.HotFervorHelmet,
            KnownSkyblockItemId.HotHollowHelmet,
            KnownSkyblockItemId.EmptyMap,
            KnownSkyblockItemId.BarrysMontgrayPen,
            KnownSkyblockItemId.CaducousExtract,
            KnownSkyblockItemId.LivingMetalAnchor,
            KnownSkyblockItemId.PreDigestionFish,
            KnownSkyblockItemId.SecretRailroadPass,
            KnownSkyblockItemId.TimeShuriken,
            KnownSkyblockItemId.CarnivalDartTube,
            KnownSkyblockItemId.ChunkOfTheMoon,
            KnownSkyblockItemId.CapsaicinEyedropsOld,
            KnownSkyblockItemId.EnchantedMushroomSoup,
            KnownSkyblockItemId.FakeEmeraldAltar,
            KnownSkyblockItemId.FlowerMaelstrom,
            KnownSkyblockItemId.FramedVolcanicStonefish,

            // Fairly hard to find, might do at some point
            KnownSkyblockItemId.RubyPolishedDrillEngine,
            KnownSkyblockItemId.SapphirePolishedDrillEngine,
            KnownSkyblockItemId.DwarvenOsBlockBran,
            KnownSkyblockItemId.CelestialTimecharm,
            KnownSkyblockItemId.ChickenNEggTimecharm,
            KnownSkyblockItemId.MirrorverseTimecharm,
            KnownSkyblockItemId.SupremeTimecharm,
            KnownSkyblockItemId.BerberisFuelInjector,
            KnownSkyblockItemId.HotDog,
            KnownSkyblockItemId.AgaricusSoup,
            KnownSkyblockItemId.PolarvoidBook,
            KnownSkyblockItemId.HornsOfTorment,
            KnownSkyblockItemId.ObsidianTablet,
            KnownSkyblockItemId.PetrifiedStarfall,
            KnownSkyblockItemId.PocketIceberg,
            KnownSkyblockItemId.PureMithril,
            KnownSkyblockItemId.TitaniumTesseract,
            KnownSkyblockItemId.TungstenRegulator,
            KnownSkyblockItemId.BlackDiamond,
            KnownSkyblockItemId.BulkyStone,
            KnownSkyblockItemId.EntropySuppressor,
            KnownSkyblockItemId.FullJawFangingKit,
            KnownSkyblockItemId.LargeWalnut,
            KnownSkyblockItemId.PresumedGallonOfRedPaint,
            KnownSkyblockItemId.RustyAnchor,
            KnownSkyblockItemId.SearingStone,
            KnownSkyblockItemId.ShinyPrism,
            KnownSkyblockItemId.WoB,
            KnownSkyblockItemId.ABeginnersGuideToPesthunting,
            KnownSkyblockItemId.AbiphoneContactsTrio,
            KnownSkyblockItemId.AvariciousChalice,
            KnownSkyblockItemId.Beacon3,
            KnownSkyblockItemId.Beacon4,
            KnownSkyblockItemId.BirchForestBiomeStick,
            KnownSkyblockItemId.EndBiomeStick,
            KnownSkyblockItemId.MesaBiomeStick,
            KnownSkyblockItemId.MushroomBiomeStick,
            KnownSkyblockItemId.SavannaBiomeStick,
            KnownSkyblockItemId.BloodSoakedCoins,
            KnownSkyblockItemId.BloodStainedCoins,
            KnownSkyblockItemId.Charcoal,
            KnownSkyblockItemId.ChillTheFish,
            KnownSkyblockItemId.ClayMinionXIIUpgradeStone,
            KnownSkyblockItemId.CluckTheFish,
            KnownSkyblockItemId.CompleteCenturyCakeBundle,
            KnownSkyblockItemId.CryopowderShard,
            KnownSkyblockItemId.DeadBushOfLove,
            KnownSkyblockItemId.BitterIcedTea,
            KnownSkyblockItemId.DecentCoffee,
            KnownSkyblockItemId.KnockOffCola,
            KnownSkyblockItemId.PulpousOrangeJuice,
            KnownSkyblockItemId.RedThornleafTea,
            KnownSkyblockItemId.ScarletonPremium,
            KnownSkyblockItemId.TepidGreenTea,
            KnownSkyblockItemId.TuttiFruttiFlavoredPoison,
            KnownSkyblockItemId.DivansAlloy,
            KnownSkyblockItemId.AppleDecoration,
            KnownSkyblockItemId.BrownMushroomDecoration,
            KnownSkyblockItemId.BushDecoration,
            KnownSkyblockItemId.BeetrootDecoration,
            KnownSkyblockItemId.BerryDecoration,
            KnownSkyblockItemId.AncientFruitDecoration,
            KnownSkyblockItemId.BerryBushDecoration,
            KnownSkyblockItemId.ChestoBerryDecoration,
            KnownSkyblockItemId.LilacFruitDecoration,
            KnownSkyblockItemId.AppalledPumpkinDecoration,
            KnownSkyblockItemId.EccentricPaintingBundle,
            KnownSkyblockItemId.EggHunt,
            KnownSkyblockItemId.EnchantedBookBundleVicious,
            KnownSkyblockItemId.EnchantedBookBundleBigBrain,
            KnownSkyblockItemId.EnchantedBookBundleReflection,
            KnownSkyblockItemId.EnchantedBookBundleTheOne,
            KnownSkyblockItemId.EnchantedBookBundleChimera,
            KnownSkyblockItemId.EnchantedBookBundlePrismatic,
            KnownSkyblockItemId.EnchantedBookshelf,
            KnownSkyblockItemId.EnchantedClayBlock,
            KnownSkyblockItemId.EnchantedCookedSalmon,
            KnownSkyblockItemId.EnchantedRabbitHide,
            KnownSkyblockItemId.EnchantedRedSandCube,
            KnownSkyblockItemId.SpeedEnrichment,
            KnownSkyblockItemId.IntelligenceEnrichment,
            KnownSkyblockItemId.CriticalDamageEnrichment,
            KnownSkyblockItemId.CriticalChanceEnrichment,
            KnownSkyblockItemId.DefenseEnrichment,
            KnownSkyblockItemId.HealthEnrichment,
            KnownSkyblockItemId.AttackSpeedEnrichment,
            KnownSkyblockItemId.EverburningFlame,
            KnownSkyblockItemId.FishingMinionXIIUpgradeStone,
            KnownSkyblockItemId.Flames,
            KnownSkyblockItemId.DesertCrystal,
            KnownSkyblockItemId.UncommonGriffinUpgradeStone,
            KnownSkyblockItemId.RareGriffinUpgradeStone,
            KnownSkyblockItemId.EpicGriffinUpgradeStone,
            KnownSkyblockItemId.LegendaryGriffinUpgradeStone,
            KnownSkyblockItemId.HardGlass,
            KnownSkyblockItemId.HasteBlock,
            KnownSkyblockItemId.HeatCore,
            KnownSkyblockItemId.HurricaneInABottle,
            KnownSkyblockItemId.HyperCatalyst
        )

        val allInventorySkyblockItemIds =
            TestHelper.readAllSkyblockProfiles().flatMap { it }.flatMap { it.currentMembers }
                .flatMap { it.allItems }.flatMap { it.items }.filterIsInstance<SkyblockItem>().map { it.id }
                .filterNot { it is KnownSkyblockItemId.UnknownSkyblockItemId }.distinct()

        val allMuseumSkyblockItemIds =
            TestHelper.readAllMuseumData().flatMap { it.museumData.values }.flatMap { it.allItems }
                .filterIsInstance<SkyblockItem>().map { it.id }
                .filterNot { it is KnownSkyblockItemId.UnknownSkyblockItemId }.distinct()

        val allSkyblockItemIds = (allInventorySkyblockItemIds + allMuseumSkyblockItemIds).distinct()

        assertTrue(
            "Some (${KnownSkyblockItemId.entries.size - allSkyblockItemIds.size}/${KnownSkyblockItemId.entries.size}) Skyblock item(s) was/were never found in the test data: ${
                KnownSkyblockItemId.entries.filterNot { allSkyblockItemIds.contains(it) || nonExistentItems.contains(it) }
                    .map { "${it.name} (${it.apiName})" }
            }"
        ) {
            (allSkyblockItemIds + nonExistentItems).distinct().size == KnownSkyblockItemId.entries.size
        }

        assertFalse(
            "Some items that were ignored were actually found, so they don't have to be ignored anymore: ${
                allSkyblockItemIds.filter {
                    nonExistentItems.contains(
                        it
                    )
                }
            }"
        ) { allSkyblockItemIds.any { nonExistentItems.contains(it) } }

        for (skyblockProfiles in TestHelper.readAllSkyblockProfiles()) {
            for (skyblockProfile in skyblockProfiles) {
                for (member in skyblockProfile.members) {
                    member.slayer?.slayerProgress?.keys?.forEach {
                        assertIsNot<KnownSlayerType.UnknownSlayerType>(it)
                    }

                    assertIsNot<KnownSlayerType.UnknownSlayerType>(member.slayer?.activeSlayerQuest?.type)

                    member.playerData?.experience?.keys?.forEach {
                        assertIsNot<KnownSkill.UnknownSkill>(it)
                    }

                    if (member is CurrentMember) {
                        member.essence.keys.forEach {
                            assertIsNot<KnownEssenceType.UnknownEssenceType>(it)
                        }

                        member.currencies.keys.forEach {
                            assertIsNot<KnownCurrencyTypes.UnknownCurrencyType>(it)
                        }

                        member.dungeons?.dungeonTypes?.keys?.forEach {
                            assertIsNot<KnownDungeonType.UnknownDungeonType>(it)
                        }

                        member.petsData?.pets?.forEach { pet ->
                            //TODO check for pet type once implemented
                            assertIsNot<KnownPetItem.UnknownPetItem>(pet.heldItem)
                        }
                    }
                }
            }
        }
    }

    @Test
    fun testSlayerLevel() {
        assertEquals(9, KnownSlayerType.Zombie.toLevel(100000000))
        assertEquals(0, KnownSlayerType.Zombie.toLevel(1))
        assertEquals(0, KnownSlayerType.Zombie.toLevel(0))
        assertEquals(1, KnownSlayerType.Zombie.toLevel(7))
        assertEquals(5, KnownSlayerType.Zombie.toLevel(5000))
        assertEquals(4, KnownSlayerType.Zombie.toLevel(4999))
    }

    @Test
    fun testStatsOverview() {
        val apiConnection = HypixelApiConnection(strategy = ApiClientStrategy.Cache)

        val uuid = UUID.fromString("39642ffc-a7fb-4d24-a1d4-916f4cad1d98")
        val profile = TestHelper.readFullSkyblockProfile()

        (apiConnection.client as MemoryCacheApiClient).skyblockProfilesCache.store(SkyblockProfiles(uuid, profile))

        val statsOverview = apiConnection.getStatsOverview(uuid)

        assertNotNull(statsOverview)
        assertEquals("Blueberry", statsOverview.profileName)
        assertEquals(uuid, statsOverview.uuid)
        assertEquals(
            "\uD83D\uDDE1\uFE0F: Heroic Hyperion ✪✪✪✪✪➌\n" +
                    "\uD83C\uDFF9: Precise Terminator ✪✪✪✪✪➋ (Duplex 5)\n" +
                    "\uD83C\uDFF9: Hasty Terminator ✪✪✪✪✪➋ (Fatal Tempo 1)\n" +
                    "\uD83D\uDC09: [Lvl 200] Greg (Minos Relic)\n" +
                    "\uD83D\uDC09: [Lvl 181] Greg (Dwarf Turtle Shelmet)\n" +
                    "\n" +
                    "<:skyblock_level:1330399754181414994> Skyblock Level: 418.83\n" +
                    "<:diamond_sword:1330399391839686656> Skill Average: 54.78\n" +
                    "\n" +
                    "<:batphone:1330399234813329458> Slayers:\uD83E\uDDDF 9 \uD83D\uDD78\uFE0F 9 \uD83D\uDC3A 9 \uD83D\uDD2E 9 \uD83D\uDD25 9 \uD83E\uDE78 5\n" +
                    "<:redstone_key:1330398890725478510> Catacombs: 43 (Class Average 37)\n" +
                    "\n" +
                    "<:piggy_bank:1330399968221204560> Purse: 19.04m\n" +
                    "<:personal_bank:1330399998512468018> Bank: 400.72m", statsOverview.description
        )
    }

    companion object {
        private val nullResponseClient: RestApiClient
            get() {
                val client = spy(RestApiClient)
                doReturn(null).whenever(client).fetchSkyblockProfiles(any())
                return client
            }
    }
}