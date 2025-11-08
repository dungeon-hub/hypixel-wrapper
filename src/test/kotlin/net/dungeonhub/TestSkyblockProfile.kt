package net.dungeonhub

import com.google.gson.JsonArray
import net.dungeonhub.hypixel.client.CacheApiClient
import net.dungeonhub.hypixel.client.RestApiClient
import net.dungeonhub.hypixel.connection.HypixelApiConnection
import net.dungeonhub.hypixel.entities.inventory.GemstoneQuality
import net.dungeonhub.hypixel.entities.inventory.ItemStack
import net.dungeonhub.hypixel.entities.inventory.SkyblockRarity
import net.dungeonhub.hypixel.entities.inventory.items.*
import net.dungeonhub.hypixel.entities.inventory.items.id.*
import net.dungeonhub.hypixel.entities.inventory.items.special.*
import net.dungeonhub.hypixel.entities.skyblock.*
import net.dungeonhub.hypixel.entities.skyblock.currencies.KnownCurrencyTypes
import net.dungeonhub.hypixel.entities.skyblock.currencies.KnownEssenceType
import net.dungeonhub.hypixel.entities.skyblock.dungeon.KnownDungeonType
import net.dungeonhub.hypixel.entities.skyblock.misc.ProfileGameMode
import net.dungeonhub.hypixel.entities.skyblock.misc.fromSkyblockTime
import net.dungeonhub.hypixel.entities.skyblock.pet.KnownPetType
import net.dungeonhub.hypixel.entities.skyblock.slayer.KnownSlayerType
import net.dungeonhub.provider.GsonProvider
import net.dungeonhub.service.SkyblockItemHelper
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

        TestHelper.runParallel {
            Files.list(Paths.get(profilesDirectory)).toList().stream().forEach { file ->
                val fullProfilesJson = TestHelper.readFile("full-profiles/${file.name}")

                val fullProfiles = GsonProvider.gson.fromJson(fullProfilesJson, JsonArray::class.java).asList()

                for (fullProfileJson in fullProfiles) {
                    val fullProfile = fullProfileJson.toSkyblockProfile()

                    assertNotNull(fullProfile)

                    assertNotNull(fullProfile.cuteName)
                }
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

                        assertEquals(43, member.dungeons.catacombsLevel)
                        assertEquals(37.0, member.dungeons.classAverage)
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
        TestHelper.runParallel {
            TestHelper.readAllSkyblockProfiles().parallel().forEach { profiles ->
                profiles.parallelStream().forEach { profile ->
                    profile.members.filterIsInstance<CurrentMember>().forEach { member ->
                        if (member.fairySoulData != null) {
                            assertEquals(
                                member.fairySoulData.unspentSouls,
                                member.fairySoulData.totalCollected - member.fairySoulData.totalExchanged,
                                "Profile member ${member.uuid} on profile ${profile.cuteName} (${profile.profileId}) has an invalid count of fairy souls."
                            )
                        }
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
        assertNotNull(member.inventory.enderChestContent)
        assertNotNull(member.inventory.armor)
        assertNotNull(member.inventory.equipment)
        assertNotNull(member.inventory.personalVault)
        assertNotNull(member.inventory.equippedWardrobeSlot)
        assertNotNull(member.inventory.wardrobeContents)

        assertEquals(
            "Heroic Hyperion ✪✪✪✪✪➌",
            member.inventory.inventoryContents!!.items.filterNotNull().first().rawName
        )
        assertEquals("Abiphone XII Mega", member.inventory.inventoryContents.items.filterNotNull().last().rawName)
    }

    @Test
    fun testItemDataParsing() {
        TestHelper.runParallel {
            TestHelper.readAllSkyblockProfiles().parallel().forEach { profiles ->
                profiles.parallelStream().forEach { profile ->
                    profile.members.filterIsInstance<CurrentMember>().parallelStream().forEach { member ->
                        if (member.inventory != null) {
                            val inventory = member.inventory

                            checkItems(inventory.inventoryContents?.items?.filterNotNull() ?: emptyList())
                            checkItems(inventory.enderChestContent?.items?.filterNotNull() ?: emptyList())
                            inventory.backpackIcons.values.forEach { checkItems(it.items.filterNotNull()) }
                            inventory.bagContents.values.forEach { checkItems(it.items.filterNotNull()) }
                            checkItems(inventory.armor?.items?.filterNotNull() ?: emptyList())
                            checkItems(inventory.equipment?.items?.filterNotNull() ?: emptyList())
                            checkItems(inventory.personalVault?.items?.filterNotNull() ?: emptyList())
                            inventory.backpackContents.values.forEach { checkItems(it.items.filterNotNull()) }
                            checkItems(inventory.wardrobeContents?.items?.filterNotNull() ?: emptyList())
                        }
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
            assertDoesNotThrow { item.lore }
            assertDoesNotThrow { item.durabilityLost }
            assertDoesNotThrow { item.count }

            if (item is SkyblockItem) {
                assertNotNull(item.id)
                assertIsNot<UnknownSkyblockItemId>(
                    item.id,
                    "Item " + item.rawName + "(" + item.id.apiName + ") isn't mapped"
                )

                if (item is Gear) {
                    assertTrue(item.runes.isEmpty() || item.runes.size == 1)
                    assertDoesNotThrow { item.dungeonSkillRequirement }
                }

                if (item is ItemWithGems) {
                    assertTrue(item.gems == null || item.gems!!.appliedGemstones.values.all {
                        GemstoneQuality.entries.contains(
                            it.gemstoneQuality
                        )
                    })
                }

                if (item is EnchantableItem) {
                    assertNotNull(item.enchantments)
                    item.enchantments.forEach {
                        assertIsNot<KnownEnchantment.UnknownEnchantment>(
                            it.key,
                            "Item ${item.id.apiName} has enchantments (${it.key.apiName}), but the wrapper doesn't acknowledge that!"
                        )
                    }
                } else {
                    assertTrue("Item ${item.id.apiName} has enchantments, but the wrapper doesn't acknowledge that!") {
                        !item.extraAttributes.contains(
                            "enchantments"
                        )
                    }
                }

                if (item is WitherBlade) {
                    assertNotNull(item.abilityScrolls)
                } else {
                    assertTrue { !item.extraAttributes.contains("ability_scroll") }
                }

                if (item is ItemWithAttributes) {
                    assertNotNull(item.attributes)
                } else {
                    assertTrue("Item ${item.id.apiName} has attributes, but the wrapper doesn't acknowledge that!") {
                        !item.extraAttributes.contains(
                            "attributes"
                        )
                    }
                }

                if (item is NewYearCakeBag) {
                    assertNotNull(item.newYearCakeBagData)
                    checkItems(item.newYearCakeBagData)
                }

                if (item is PersonalCompactor) {
                    item.compactSlots.forEach {
                        assertIsNot<UnknownSkyblockItemId>(
                            it,
                            "Unknown item ID in Personal Compactor: ${it.apiName}"
                        )
                    }
                }

                if (item is PersonalDeletor) {
                    item.deletorSlots.forEach {
                        assertIsNot<UnknownSkyblockItemId>(
                            it,
                            "Unknown item ID in Personal Compactor: ${it.apiName}"
                        )
                    }
                }

                if (item is BuildersWand) {
                    assertNotNull(item.buildersWandData)
                }

                if (item is BuildersRuler) {
                    assertNotNull(item.buildersRulerData)
                }

                if (item is SkinAppliable) {
                    assertIsNot<KnownDyeId.UnknownDyeId>(item.appliedDye, "Unknown dye ${item.appliedDye?.apiName}")
                    //TODO reenable once everything is mapped
                    //assertIsNot<UnknownSkinItemId>(item.appliedSkin)
                }

                if (item is BottleOfJyrre) {
                    assertDoesNotThrow { item.lastUpdated }
                    assertDoesNotThrow { item.secondsHeld }
                }

                if (item is JournalEntry) {
                    assertDoesNotThrow { item.journalType }
                }

                if (item is EditionItem) {
                    assertDoesNotThrow { item.recipientName }
                    assertDoesNotThrow { item.recipientUuid }
                    assertDoesNotThrow { item.dateObtained }
                    assertDoesNotThrow { item.edition }
                }

                if (item is CenturyMemento) {
                    assertDoesNotThrow { item.tickets }
                    assertDoesNotThrow { item.winningTeam }
                    assertDoesNotThrow { item.recipientTeam }
                    assertTrue { item.id.apiName.endsWith(item.recipientTeam) }
                }

                if (item is BingosSecretMemento) {
                    assertDoesNotThrow { item.goalName }
                    assertDoesNotThrow { item.bingoEvent }
                }

                if (item is PandorasBox) {
                    assertDoesNotThrow { item.pandoraRarity }
                }

                if (item is Drill) {
                    assertDoesNotThrow { item.upgradeModule }
                    assertIsNot<UnknownSkyblockItemId>(item.upgradeModule)
                    if (item.upgradeModule != null) assertIs<DrillPartId>(item.upgradeModule)

                    assertDoesNotThrow { item.engine }
                    assertIsNot<UnknownSkyblockItemId>(item.engine)
                    if (item.engine != null) assertIs<DrillPartId>(item.engine)

                    assertDoesNotThrow { item.fuelTank }
                    assertIsNot<UnknownSkyblockItemId>(item.fuelTank)
                    if (item.fuelTank != null) assertIs<DrillPartId>(item.fuelTank)
                }

                if (item is Sprayonator) {
                    assertDoesNotThrow { item.sprayItem }
                    assertIsNot<UnknownSkyblockItemId>(item.sprayItem)
                }

                if (item is KuudraTeethPlaque) {
                    assertDoesNotThrow { item.rarity }
                }

                if (item is FishingTool) {
                    assertDoesNotThrow { item.lavaCreaturesKilled }
                }

                if (item is RaffleTicket) {
                    assertDoesNotThrow { item.raffleUUID }
                }

                if (item is Postcard) {
                    assertDoesNotThrow { item.minionTier }
                    assertNotNull(item.minionTier)
                    assertDoesNotThrow { item.minionType }
                    assertNotNull(item.minionType)
                }

                if (item is NecromancyItem) {
                    assertDoesNotThrow { item.souls }
                }

                if (item is Backpack) {
                    assertDoesNotThrow { item.backpackData?.items }
                    item.backpackData?.items?.let { checkItems(it.filterNotNull()) }
                }

                if (item is RefundBoosterCookie) {
                    assertDoesNotThrow { item.playerId }
                }

                if (item is BasketOfSeeds) {
                    assertDoesNotThrow { item.content }
                    checkItems(item.content.filterNotNull())
                }

                if (item is NetherWartPouch) {
                    assertDoesNotThrow { item.content }
                    checkItems(item.content.filterNotNull())
                }

                if (item is DittoBlob) {
                    assertDoesNotThrow { item.originalItemId }
                    assertIsNot<UnknownSkyblockItemId>(item.originalItemId)
                }

                if (item is TimeBagItem) {
                    assertDoesNotThrow { item.lastForceEvolvedTime }
                }

                if (item is FishingRod) {
                    assertDoesNotThrow { item.hook }
                    assertDoesNotThrow { item.line }
                    assertDoesNotThrow { item.sinker }
                }

                if (item is BucketOfDye) {
                    assertDoesNotThrow { item.donatedDye }
                    assertIsNot<KnownDyeId.UnknownDyeId>(item.donatedDye)
                }

                if (item is PetAsItem) {
                    assertDoesNotThrow { item.petInfo?.skin }
                    assertIsNot<KnownPetSkinId.UnknownPetSkinId>(item.petInfo?.skin)
                }

                if (item is ItemWithAbility) {
                    assertIsNot<KnownAbilityScrollId.UnknownAbilityScroll>(item.abilityScroll)
                }

                if (item is Accessory) {
                    assertIsNot<KnownEnrichmentId.UnknownEnrichment>(
                        item.enrichment,
                        "Unknown enrichment id ${item.enrichment?.appliedId}"
                    )
                }

                SkyblockItemHelper.checkFields(item)
            }
        }
    }

    @Test
    fun checkSkyblockMenuPresence() {
        TestHelper.runParallel {
            TestHelper.readAllSkyblockProfiles().parallel().forEach { profiles ->
                profiles.forEach { profile ->
                    profile.members.filterIsInstance<CurrentMember>().parallelStream().forEach { member ->
                        if (member.inventory != null && member.inventory.inventoryContents != null) {
                            val inventoryContent = member.inventory.inventoryContents.items

                            val skyblockMenu = inventoryContent[8]

                            if (skyblockMenu != null) {
                                //apparently some profiles still exist with this outdated item name :/
                                if (skyblockMenu.name == "§aSkyBlock Menu §7(Right Click)") {
                                    assertEquals("§aSkyBlock Menu §7(Right Click)", skyblockMenu.name)
                                    assertEquals("SkyBlock Menu (Right Click)", skyblockMenu.rawName)
                                } else if (skyblockMenu.name == "§bMagical Map") { //Dungeons Map
                                    assertEquals("§bMagical Map", skyblockMenu.name)
                                    assertEquals("Magical Map", skyblockMenu.rawName)
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
        val allItems = TestHelper.readItemList()

        val allItemIds = allItems.map { it.getAsJsonPrimitive("id").asString }

        allItemIds.map { KnownSkyblockItemId.fromApiName(it) }.forEach { id ->
            assertIsNot<UnknownSkyblockItemId>(
                id,
                "Item ${id.apiName} has not been added to Skyblock item ID list",
            )
        }
    }

    @Test
    fun testAllItemsInTestData() {
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

        val duplicateSkyblockItemNames = HashSet<String>()
        val uniqueSkyblockItemNames = HashSet<String>()

        for (item in KnownSkyblockItemId.entries.map { it.name }) {
            if (!uniqueSkyblockItemNames.add(item)) {
                duplicateSkyblockItemNames.add(item)
            }
        }

        assertTrue("Some Skyblock item IDs were used multiple times in some enums: $duplicateSkyblockItemNames.") {
            duplicateSkyblockItemNames.isEmpty() && uniqueSkyblockItemNames.size == KnownSkyblockItemId.entries.size
        }

        val allInventorySkyblockItemIds =
            TestHelper.readAllSkyblockProfiles().parallel().flatMap { it.stream() }
                .flatMap { it.currentMembers.stream() }.flatMap { it.allItems.stream() }.flatMap { it.items.stream() }
                .filter { it is SkyblockItem }.map { (it as SkyblockItem).id }.filter { it !is UnknownSkyblockItemId }
                .distinct().toList()

        val allMuseumSkyblockItemIds =
            TestHelper.readAllMuseumData().flatMap { it.museumData.values }.flatMap { it.allItems }
                .filterIsInstance<SkyblockItem>().map { it.id }
                .filterNot { it is UnknownSkyblockItemId }.distinct()

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
    }

    @Test
    fun testAllPetsInTestData() {
        TestHelper.runParallel {
            val allPetTypes = TestHelper.readAllSkyblockProfiles().parallel().flatMap { profiles ->
                profiles.parallelStream().flatMap { profile ->
                    (profile.members.filterIsInstance<CurrentMember>().mapNotNull { it.petsData?.pets }
                        .flatMap { it } + profile.members.filterIsInstance<CurrentMember>()
                        .mapNotNull { it.riftData?.deadCats?.montezuma }).map { it.type }.stream()
                }
            }.distinct().toList()

            val missingPetTypes = KnownPetType.entries.filter { !allPetTypes.contains(it) }

            assertTrue(
                "Some pet types weren't included in the test data: ${missingPetTypes.map { it.apiName }}",
            ) { missingPetTypes.isEmpty() }
        }
    }

    @Test
    fun testKnownPetSkinIds() {
        KnownPetSkinId.entries.forEach {
            assertDoesNotThrow { it.appliedName }
            assertNotNull(it.appliedName)

            assertTrue { it.apiName.startsWith("PET_SKIN_") }
        }
    }

    @Test
    fun testNoInvalidDataTypes() {
        TestHelper.runParallel {
            TestHelper.readAllSkyblockProfiles().parallel().forEach { profiles ->
                profiles.parallelStream().forEach { profile ->
                    for (member in profile.members) {
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

                            member.petsData?.petCare?.petsSacrificed?.forEach { petType ->
                                assertIsNot<KnownPetType.UnknownPetType>(
                                    petType,
                                    "Pet ${petType.apiName} isn't recognized"
                                )
                            }

                            member.petsData?.pets?.forEach { pet ->
                                assertIsNot<KnownPetType.UnknownPetType>(
                                    pet.type,
                                    "Pet ${pet.type.apiName} isn't recognized"
                                )
                                assertIsNot<KnownPetItem.UnknownPetItem>(
                                    pet.heldItem,
                                    "Pet item ${pet.heldItem?.apiName} isn't recognized"
                                )
                                assertIsNot<KnownPetSkinId.UnknownPetSkinId>(
                                    pet.skin,
                                    "Pet skin ${pet.skin?.apiName} isn't recognized"
                                )
                            }

                            member.riftData?.deadCats?.montezuma?.let { pet ->
                                assertIsNot<KnownPetType.UnknownPetType>(
                                    pet.type,
                                    "Pet ${pet.type.apiName} isn't recognized"
                                )
                                assertIsNot<KnownPetItem.UnknownPetItem>(pet.heldItem)
                            }

                            member.playerStats?.auctions?.let { auctionStats ->
                                assertDoesNotThrow { auctionStats.totalBought }
                                if (auctionStats.totalBought.size > 1 /*sometimes it doesn't contain a total - why?*/ && auctionStats.totalBought.keys.any { it == null }) {
                                    assertEquals(1, auctionStats.totalBought.keys.count { it == null })
                                }

                                assertDoesNotThrow { auctionStats.totalSold }
                                if (auctionStats.totalSold.size > 1 && auctionStats.totalSold.keys.any { it == null }) {
                                    assertEquals(1, auctionStats.totalSold.keys.count { it == null })
                                }
                            }
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

        (apiConnection.client as CacheApiClient).skyblockProfilesCache.store(SkyblockProfiles(uuid, profile))

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
                    "\uD83D\uDCAA Magical Power: 1469\n" +
                    "\n" +
                    "<:batphone:1330399234813329458> Slayers:\uD83E\uDDDF 9 \uD83D\uDD78\uFE0F 9 \uD83D\uDC3A 9 \uD83D\uDD2E 9 \uD83D\uDD25 9 \uD83E\uDE78 5\n" +
                    "<:redstone_key:1330398890725478510> Catacombs: 43 (Class Average 37)\n" +
                    "\n" +
                    "<:piggy_bank:1330399968221204560> Purse: 19.04m\n" +
                    "<:personal_bank:1330399998512468018> Bank: 400.72m", statsOverview.description
        )
    }

    @Test
    fun testBingoRankCalculation() {
        val bingoRanks = mapOf(
            UUID.fromString("c932b869-d479-4471-a36d-4ec6c1ef1fa2") to SkyblockRarity.Legendary,
            UUID.fromString("39642ffc-a7fb-4d24-a1d4-916f4cad1d98") to SkyblockRarity.Legendary,
            UUID.fromString("92d940ad-10e8-4d9d-a795-adcf5fd6b0c6") to SkyblockRarity.Legendary,
            UUID.fromString("0425ae3e-3be4-4761-81e0-45be04ad2606") to SkyblockRarity.Uncommon,
            UUID.fromString("91821440-2b71-4cdb-8364-611c3e435e4b") to SkyblockRarity.Epic,
        )

        TestHelper.runParallel {
            TestHelper.readAllSkyblockProfileObjects().forEach {
                assertDoesNotThrow { it.bingoRank }

                if (bingoRanks.containsKey(it.owner)) {
                    assertEquals(bingoRanks[it.owner], it.bingoRank)
                }
            }
        }
    }

    companion object {
        private val nullResponseClient: RestApiClient
            get() {
                val client = spy(RestApiClient)
                doReturn(null).whenever(client).fetchSkyblockProfiles(any())
                return client
            }

        val nonExistentItems = listOf(
            // These are unobtainable :(
            AdminItemId.AdminLavaRod,
            AdminItemId.AdminsWarmSweater,
            ArmorItemId.BootsOfTheStars,
            AdminItemId.BowOfTheUniverse,
            AdminItemId.EnchantedClockAdmin,
            AccessoryItemId.EternalCrystal,
            AdminItemId.FerocitySword150,
            AdminItemId.FerocitySword50,
            AdminItemId.GiantPileOfCash,
            ArmorItemId.HelmetOfTheStars,
            AdminItemId.Kindred,
            ArmorItemId.PotatoCrown,
            ArmorItemId.ShimmersparkleChestplate,
            MementoItemId.ShinyRelic,
            AdminItemId.SwordOfTheMultiverse,
            AdminItemId.SwordOfTheStars9000,
            AdminItemId.SwordOfTheUniverse,
            AdminItemId.Voodoo,
            RiftItemId.RemoteTimeChamberRemote,
            WandItemId.HellstormWand,
            VanillaItemId.MinecartWithCommandBlock,
            AdminItemId.SwordOfLiveReloading,
            AdminItemId.HelmetOfLiveReloading,
            WeaponItemId.GiantsEyeSword,
            MiscItemId.KuudraWashingMachine,
            MiscItemId.DragonEgg,
            ArmorItemId.BoopfishHelmet,
            AdminItemId.FestiveFrisbee,
            MiscItemId.HostageCompass,
            DisplayItemId.MinosHunterChestplate,
            DisplayItemId.SeaWalkerChestplate,
            KnownPetItem.AllSkillsExpEpic,
            DisplayItemId.DeadgehogHelmet,
            AccessoryItemId.LuckTalisman,
            ArmorItemId.NetheriteHelmet,
            DisplayItemId.CatacombsPass0,
            DisplayItemId.CatacombsPass1,
            DisplayItemId.CatacombsPass2,
            DisplayItemId.CatacombsPass3,
            DisplayItemId.CatacombsPass4,
            DisplayItemId.CatacombsPass5,
            DisplayItemId.CatacombsPass6,
            DisplayItemId.CatacombsPass7,
            DisplayItemId.CatacombsPass8,
            DisplayItemId.CatacombsPass9,
            DisplayItemId.CatacombsPass10,
            DisplayItemId.MushroomCollection,
            DisplayItemId.CancelParkour,
            DisplayItemId.ExplosiveShotAbility,
            AdminItemId.TreeHook,
            AdminItemId.NeurotoxinNeedle,
            KnownPetSkinId.OrangeSheepSkin,
            DisplayItemId.MediumPotionBag,
            KnownPetItem.FarmingExpLegendary,
            AdminItemId.DashingBoots,
            AccessoryItemId.BingoHeirloom,
            AdminItemId.LargeRunesSack,
            AdminItemId.MediumRunesSack,
            AdminItemId.SmallRunesSack,
            ArmorItemId.CryptWitherlordHelmet,
            ArmorItemId.CryptWitherlordChestplate,
            ArmorItemId.CryptWitherlordLeggings,
            ArmorItemId.CryptWitherlordBoots,
            DisplayItemId.CancelRace,
            DisplayItemId.GemstoneCollection,
            DisplayItemId.KeyX,
            MementoItemId.CoinsOnFire,
            MiscItemId.BlackGreaterBackpack,
            MiscItemId.BlackLargeBackpack,
            MiscItemId.BlackMediumBackpack,
            MiscItemId.BlackSmallBackpack,
            MiscItemId.BlueGreaterBackpack,
            MiscItemId.BlueLargeBackpack,
            MiscItemId.BlueMediumBackpack,
            MiscItemId.BlueSmallBackpack,
            MiscItemId.BrownGreaterBackpack,
            MiscItemId.BrownLargeBackpack,
            MiscItemId.BrownMediumBackpack,
            MiscItemId.BrownSmallBackpack,
            MiscItemId.CyanGreaterBackpack,
            MiscItemId.CyanLargeBackpack,
            MiscItemId.CyanMediumBackpack,
            MiscItemId.CyanSmallBackpack,
            MiscItemId.GreenGreaterBackpack,
            MiscItemId.GreenLargeBackpack,
            MiscItemId.GreenMediumBackpack,
            MiscItemId.GreenSmallBackpack,
            MiscItemId.GreyGreaterBackpack,
            MiscItemId.GreyLargeBackpack,
            MiscItemId.GreyMediumBackpack,
            MiscItemId.GreySmallBackpack,
            MiscItemId.LightBlueGreaterBackpack,
            MiscItemId.LightBlueLargeBackpack,
            MiscItemId.LightBlueMediumBackpack,
            MiscItemId.LightBlueSmallBackpack,
            MiscItemId.LightGreyGreaterBackpack,
            MiscItemId.LightGreyLargeBackpack,
            MiscItemId.LightGreyMediumBackpack,
            MiscItemId.LightGreySmallBackpack,
            MiscItemId.LimeGreaterBackpack,
            MiscItemId.LimeLargeBackpack,
            MiscItemId.LimeMediumBackpack,
            MiscItemId.LimeSmallBackpack,
            MiscItemId.MagentaGreaterBackpack,
            MiscItemId.MagentaLargeBackpack,
            MiscItemId.MagentaMediumBackpack,
            MiscItemId.MagentaSmallBackpack,
            MiscItemId.OrangeGreaterBackpack,
            MiscItemId.OrangeLargeBackpack,
            MiscItemId.OrangeMediumBackpack,
            MiscItemId.OrangeSmallBackpack,
            MiscItemId.PinkGreaterBackpack,
            MiscItemId.PinkLargeBackpack,
            MiscItemId.PinkMediumBackpack,
            MiscItemId.PinkSmallBackpack,
            MiscItemId.PurpleGreaterBackpack,
            MiscItemId.PurpleLargeBackpack,
            MiscItemId.PurpleMediumBackpack,
            MiscItemId.PurpleSmallBackpack,
            MiscItemId.RedGreaterBackpack,
            MiscItemId.RedLargeBackpack,
            MiscItemId.RedMediumBackpack,
            MiscItemId.RedSmallBackpack,
            MiscItemId.WhiteGreaterBackpack,
            MiscItemId.WhiteLargeBackpack,
            MiscItemId.WhiteMediumBackpack,
            MiscItemId.WhiteSmallBackpack,
            MiscItemId.YellowGreaterBackpack,
            MiscItemId.YellowLargeBackpack,
            MiscItemId.YellowMediumBackpack,
            MiscItemId.YellowSmallBackpack,
            AdminItemId.TalismanOfSpace,
            AdminItemId.FairySoulsPamphlet,
            AdminItemId.IronAlloy,
            AdminItemId.OverchewedDetritus,
            AdminItemId.TNTCharge,
            CosmeticItemId.HappyEmojiMinionSkin,
            CosmeticItemId.MilkAndCookies,
            DisplayItemId.AmethystCrystal,
            DisplayItemId.LeaveChallenge,
            DisplayItemId.KeyA,
            DisplayItemId.KeyB,
            DisplayItemId.KeyC,
            DisplayItemId.KeyD,
            DisplayItemId.KeyF,
            DisplayItemId.KeyFragment,
            DisplayItemId.KeyS,
            DisplayItemId.LargeTalismanBag,
            DisplayItemId.SmallTalismanBag,
            DisplayItemId.MithrilPowderTier1,
            DisplayItemId.MithrilPowderTier2,
            DisplayItemId.MithrilPowderTier3,
            DisplayItemId.GemstonePowderTier1,
            DisplayItemId.GemstonePowderTier2,
            DisplayItemId.GemstonePowderTier3,
            DisplayItemId.VanguardChestplate,
            DisplayItemId.EmperorHead,
            DisplayItemId.WarriorDungeonAbility1,
            DisplayItemId.HealerDungeonAbility3,
            DisplayItemId.SeaArcherHelmet,
            DisplayItemId.TopazCrystal,
            DisplayItemId.DungeonBossKey,
            DisplayItemId.SwappablePreview,
            DisplayItemId.HauntAbility,
            DisplayItemId.WatcherChestplate,
            DisplayItemId.ResourceCrystal,
            DisplayItemId.RubyCrystal,
            AdminItemId.RandomFirework,
            DisplayItemId.MinosHunterBoots,
            CosmeticItemId.EnderMinionSkin,
            VanillaItemId.IronHorseArmor,
            MiscItemId.PortableBuilder,
            KnownAbilityScrollId.JadePowerScroll,
            KnownAbilityScrollId.TopazPowerScroll,
            MiscItemId.NetherIsland,
            KnownPetItem.ForagingExpUncommon,
            VanillaItemId.BlueBanner,
            VanillaItemId.BrownBanner,
            VanillaItemId.CyanBanner,
            VanillaItemId.GrayBanner,
            VanillaItemId.GreenBanner,
            VanillaItemId.LightBlueBanner,
            VanillaItemId.LightGrayBanner,
            VanillaItemId.LimeBanner,
            VanillaItemId.MagentaBanner,
            VanillaItemId.OrangeBanner,
            VanillaItemId.PinkBanner,
            VanillaItemId.PurpleBanner,
            VanillaItemId.RedBanner,
            VanillaItemId.WhiteBanner,
            VanillaItemId.YellowBanner,
            DisplayItemId.SkeletonLordBow,
            AdminItemId.PerfectHopper,
            AdminItemId.BoxOfBoosterCookies,
            AdminItemId.GiantFlesh,
            AdminItemId.GiantTheFish,
            AdminItemId.PetSatchel,
            AdminItemId.TitaniumAlloy,
            AdminItemId.RedSheepSkin,
            AdminItemId.RottenApple,
            AdminItemId.FireSoul,
            AdminItemId.LightGraySheepSkin,
            AdminItemId.GreenSheepSkin,
            AdminItemId.YellowSheepSkin,
            AdminItemId.BlueSheepSkin,
            AdminItemId.MagentaSheepSkin,
            AdminItemId.GraySheepSkin,
            AdminItemId.EmptySodaCan,
            AdminItemId.RingOfSpace,
            AdminItemId.ArtifactOfSpace,
            AdminItemId.WandofRecall,
            AdminItemId.GhastChestplate,
            AdminItemId.GhastLeggings,
            AdminItemId.GhastBoots,
            AdminItemId.MasterSkullTierEight,
            AdminItemId.MasterSkullTierNine,
            AdminItemId.MasterSkullTierTen,
            BuggedItemId.BoneMeal2,
            BuggedItemId.EnchantedCarrotOnAStick2,
            AdminItemId.EndermanHat,
            AdminItemId.CompassTalisman,
            AdminItemId.BrokenHandle,
            AdminItemId.EpeeDePatrice,
            AdminItemId.ChewedPieceOfGum,
            AdminItemId.BlueCandy,
            AdminItemId.GreaterStorageUpgrade,
            AdminItemId.PolymorphWand,
            AdminItemId.RedCrossPotion,
            AdminItemId.FairyFlute,
            AdminItemId.JarOfPickles,
            AdminItemId.MusicDiscRevenge,
            AdminItemId.DirtyRug,
            AdminItemId.MagmaBucketUpgrade,
            AdminItemId.ClownTheFish,
            AdminItemId.MoldyBread,
            AdminItemId.GrizzlyPaw,
            AdminItemId.FairyWingsCharm,
            AdminItemId.ElectricWand,
            AdminItemId.Firestone,
            AdminItemId.BottleOfNightmare,
            AdminItemId.EnchantedBeef,
            AdminItemId.VenomousSword,
            AdminItemId.BigSpringBoots,
            AdminItemId.BiggerSpringBoots,
            AdminItemId.Hammer,
            AdminItemId.AimingBow,
            AdminItemId.MithrilTech,
            AdminItemId.MiningIsland,
            AdminItemId.EnchantedGriffinFeather,
            AdminItemId.FreshSpringWater,
            AdminItemId.RandomBook,
            AdminItemId.BarnIsland,
            AdminItemId.HarvestingSword,
            AdminItemId.OldBoot,
            AdminItemId.IncorrectSnubfinDolphinSkin,
            AdminItemId.FireFlowerTool,
            AdminItemId.FakeGoldRing,
            AdminItemId.CottonCandy,
            AdminItemId.TravelScrollToTheBlazeFarm,
            AdminItemId.AutowandSleeve,
            AdminItemId.TravelScrollToDragontail,
            AdminItemId.TravelScrollToTheKuudraPassage,
            AdminItemId.TravelScrollToScarleton,
            AdminItemId.OrganicBrownMushroom,
            AdminItemId.OrganicCactus,
            AdminItemId.OrganicCarrot,
            AdminItemId.OrganicCocoaBeans,
            AdminItemId.OrganicEgg,
            AdminItemId.OrganicMelon,
            AdminItemId.OrganicMilk,
            AdminItemId.OrganicMushroom,
            AdminItemId.OrganicNetherWart,
            AdminItemId.OrganicPotato,
            AdminItemId.OrganicPumpkin,
            AdminItemId.OrganicRedMushroom,
            AdminItemId.OrganicSugarCane,
            AdminItemId.OrganicWheat,
            AdminItemId.OrganicWool,
            AdminItemId.Copper,
            AdminItemId.Fertilizer,
            AdminItemId.GatewayWand,
            AdminItemId.ArgofayTrinket,
            AdminItemId.TestVampireChestplate,
            AdminItemId.TestRiftWand,
            AdminItemId.DraconicBlade,
            AdminItemId.SeismicWaveStick,
            AdminItemId.MagicalStaff,
            AdminItemId.DiamondRefinery,
            AdminItemId.SpellbookTest,
            DisplayItemId.BossSpiritBow,
            DisplayItemId.DungeonRedSupportOrb,
            DisplayItemId.GhostAxeAbility,
            DisplayItemId.HarvestingUpgrade,
            DisplayItemId.HorsemansScythe,
            DisplayItemId.MediumTalismanBag,
            DisplayItemId.PurchaseRevivePerk,
            DisplayItemId.ReviveDeadPerk,
            DisplayItemId.ReviveFinalKillPerk,
            DisplayItemId.ReviveStoneOrb,
            DisplayItemId.SapphireCrystal,
            DisplayItemId.ThrowingAxeAbility,
            DisplayItemId.UnknownItem,
            DisplayItemId.VanguardBoots,
            DisplayItemId.VanguardLeggings,
            DisplayItemId.WatcherBoots,
            CosmeticItemId.SlothMinionSkin,
            DisplayItemId.JadeCrystal,
            DisplayItemId.CitrineCrystal,
            DisplayItemId.AquamarineCrystal,
            DisplayItemId.OpalCrystal,
            DisplayItemId.PeridotCrystal,
            DisplayItemId.OnyxCrystal,
            DisplayItemId.MasterCatacombsPass0,
            DisplayItemId.MasterCatacombsPass1,
            DisplayItemId.MasterCatacombsPass2,
            DisplayItemId.MasterCatacombsPass3,
            DisplayItemId.MasterCatacombsPass4,
            DisplayItemId.MasterCatacombsPass5,
            DisplayItemId.MasterCatacombsPass6,
            DisplayItemId.MasterCatacombsPass7,
            DisplayItemId.MasterCatacombsPass8,
            DisplayItemId.MasterCatacombsPass9,
            DisplayItemId.MasterCatacombsPass10,
            DisplayItemId.EmperorRobes,
            DisplayItemId.EmperorLeggings,
            DisplayItemId.EmperorShoes,
            DisplayItemId.HealerDungeonAbility1,
            DisplayItemId.HealerDungeonAbility2,
            DisplayItemId.SeaWalkerHelmet,
            DisplayItemId.SeaWalkerLeggings,
            DisplayItemId.SeaWalkerBoots,
            DisplayItemId.HealingCircle,
            DisplayItemId.MaxorEnergyCrystal,
            DisplayItemId.ArcherDungeonAbility1,
            DisplayItemId.ArcherDungeonAbility2,
            DisplayItemId.ArcherUltimateAbility,
            DisplayItemId.MageDungeonAbility2,
            DisplayItemId.MageDungeonAbility3,
            DisplayItemId.TankDungeonAbility1,
            DisplayItemId.TankDungeonAbility2,
            DisplayItemId.RagnarokAbility,
            DisplayItemId.FullFairyControl,
            DisplayItemId.CastleOfStone,
            DisplayItemId.WaterHydraChestplate,
            DisplayItemId.WaterHydraLeggings,
            DisplayItemId.DungeonNormalKey,
            DisplayItemId.WaterHydraBoots,
            DisplayItemId.WishUltimate,
            DisplayItemId.MinosHunterHelmet,
            DisplayItemId.MinosHunterLeggings,
            DisplayItemId.VanguardHelmet,
            DisplayItemId.WatcherEye,
            DisplayItemId.DungeonBlueSupportOrb,
            DisplayItemId.DungeonGreenSupportOrb,
            DisplayItemId.CancelWizardmanRace,
            DisplayItemId.ElleFuelCell,
            DisplayItemId.ElleSupplies,
            DisplayItemId.MiniatureNuke,
            DisplayItemId.WatcherLeggings,
            DisplayItemId.MiningCore,
            MiscItemId.HalfEatenProteinBar,
            MiscItemId.EnchantedClownfishIncorrect,
            KnownPetItem.MiningExpEpic,
            KnownPetItem.MiningExpLegendary,
            KnownPetItem.FishingExpLegendary,
            KnownPetItem.CombatExpLegendary,
            KnownPetItem.ForagingExpRare,
            KnownPetItem.ForagingExpLegendary,
            KnownPetItem.AllSkillsExpUncommon,
            KnownPetItem.AllSkillsExpRare,
            KnownPetItem.AllSkillsExpLegendary,
            KnownPetItem.BigTeethUncommon,
            KnownPetItem.BigTeethRare,
            KnownPetItem.BigTeethEpic,
            KnownPetItem.BigTeethLegendary,
            KnownPetItem.IronClawsUncommon,
            KnownPetItem.IronClawsRare,
            KnownPetItem.IronClawsEpic,
            KnownPetItem.IronClawsLegendary,
            KnownPetItem.HardenedScalesRare,
            KnownPetItem.HardenedScalesEpic,
            KnownPetItem.HardenedScalesLegendary,
            KnownPetItem.SharpenedClawsUncommon,
            KnownPetItem.SharpenedClawsRare,
            KnownPetItem.SharpenedClawsEpic,
            KnownPetItem.SharpenedClawsLegendary,
            VanillaItemId.CoalOre,
            VanillaItemId.DiamondHorseArmor,
            VanillaItemId.CreeperSkull,

            // These are quite impossible to find
            ArmorItemId.BurningHollowHelmet,
            ArmorItemId.FieryFervorBoots,
            ArmorItemId.FieryFervorChestplate,
            ArmorItemId.FieryHollowHelmet,
            CosmeticItemId.GoldenDanteStatue,
            ArmorItemId.HotFervorHelmet,
            ArmorItemId.HotHollowHelmet,
            VanillaItemId.EmptyMap,
            RiftItemId.PreDigestionFish,
            WeaponItemId.TimeShuriken,
            MiscItemId.CarnivalDartTube,
            MiscItemId.ChunkOfTheMoon,
            MiscItemId.EnchantedMushroomSoup,
            MiscItemId.FlowerMaelstrom,
            MiscItemId.FramedVolcanicStonefish,
            RiftTimecharmId.ChickenNEggTimecharm,
            MiscItemId.LetterOfRecommendation,
            MiscItemId.VeryOfficialYellowRockOfLove,
            DungeonItemId.WizardsCrystal,
            ToolItemId.GameFixer,
            ToolItemId.BingoLavaRod,
            ToolItemId.CarnivalRod,
            ToolItemId.EllesLavaRod,
            ToolItemId.CarnivalShovel,
            ToolItemId.EllesPickaxe,
            RiftItemId.HotDog,
            RiftItemId.AgaricusSoup,
            PowerStoneId.HornsOfTorment,
            DungeonItemId.ReviveStone,
            MiscItemId.SkeletonTheFish,
            MiscItemId.MegaJerryBox,

            // Fairly hard to find, might do at some point
            ForgeableItemId.TungstenRegulator,
            ReforgeStoneId.BlackDiamond,
            ReforgeStoneId.FullJawFangingKit,
            ReforgeStoneId.PresumedGallonOfRedPaint,
            ReforgeStoneId.RustyAnchor,
            MiscItemId.AvariciousChalice,
            MiscItemId.Beacon4,
            MiscItemId.BirchForestBiomeStick,
            MiscItemId.ChillTheFish1,
            MiscItemId.ClayMinionXIIUpgradeStone,
            MiscItemId.DeadBushOfLove,
            PotionItemId.BitterIcedTea,
            PotionItemId.RedThornleafTea,
            PotionItemId.ScarletonPremium,
            MiscItemId.BrownMushroomDecoration,
            MiscItemId.BerryBushDecoration,
            MiscItemId.EccentricPaintingBundle,
            MiscItemId.EnchantedBookBundleVicious,
            MiscItemId.EnchantedBookBundleBigBrain,
            MiscItemId.EnchantedBookBundleChimera,
            MiscItemId.EnchantedBookBundlePrismatic,
            MiscItemId.EnchantedBookBundleTransylvanian,
            KnownEnrichmentId.IntelligenceEnrichment,
            KnownEnrichmentId.DefenseEnrichment,
            KnownEnrichmentId.HealthEnrichment,
            MiscItemId.FishingMinionXIIUpgradeStone,
            MiscItemId.LegendaryGriffinUpgradeStone,
            MiscItemId.Kloonboat,
            MiscItemId.MaddoxsPhoneNumber,
            MiscItemId.MushroomWartsStew,
            MiscItemId.MyceliumDust,
            MiscItemId.PerfectlyCutDiamond,
            MiscItemId.PotatoWarSilverMedal,
            KnownAbilityScrollId.RubyPowerScroll,
            KnownAbilityScrollId.AmethystPowerScroll,
            MiscItemId.QualityMap,
            KnownPetItem.RadioactiveVial,
            MiscItemId.ReaperPepper,
            MiscItemId.SecretGiftForJuliette,
            MiscItemId.SealsTreatBag,
            MiscItemId.SolarPanel,
            MiscItemId.SoulflowEngine,
            MiscItemId.StewTheFish,
            MiscItemId.StonePlatform,
            MiscItemId.TeleporterPill,
            MiscItemId.TheArtOfPeace,
            MiscItemId.WeatherNode,
            MiscItemId.WispUpgradeStoneEpic,
            MiscItemId.WispUpgradeStoneLegendary,
            MiscItemId.PortalToTheCastle,
            MiscItemId.PortalToTheDarkAuction,
            MiscItemId.PortalToTheCrypts,
            MiscItemId.PortalToTheMuseum,
            MiscItemId.PortalToTheBarn,
            MiscItemId.PortalToMushroomIsland,
            MiscItemId.PortalToTheTrappersDen,
            MiscItemId.PortalToTheForge,
            MiscItemId.PortalToTheCrystalHollows,
            MiscItemId.PortalToTheTopOfTheNest,
            MiscItemId.PortalToArachnesSanctuary,
            MiscItemId.PortalToTheDragonsNest,
            MiscItemId.PortalToTheVoidSepulture,
            MiscItemId.PortalToTheWasteland,
            MiscItemId.PortalToDragontail,
            MiscItemId.PortalToScarleton,
            MiscItemId.PortalToTheSmolderingTomb,
            MiscItemId.PortalToTheWizardTower,
            MiscItemId.PortalToTheDwarvenBaseCamp,
            MiscItemId.PortalToTheRift,
            KnownPetItem.AllSkillsExpSuperBoost,
            KnownPetItem.BiggerTeeth,
            KnownPetItem.GoldClaws,
            KnownPetItem.HardenedScalesCommon,
            KnownPetItem.HardenedScalesUncommon,
            KnownPetItem.SharpenedClawsCommon,
            KnownPetItem.SerratedClaws,
            KnownPetItem.Bubblegum,
            KnownPetItem.TierBoost,
            KnownPetItem.FourEyedFish,
            MinionItemId.AcaciaMinion3,
            MinionItemId.AcaciaMinion5,
            MinionItemId.AcaciaMinion7,
            MinionItemId.BirchMinion1,
            MinionItemId.BirchMinion3,
            MinionItemId.BlazeMinion3,
            MinionItemId.BlazeMinion4,
            MinionItemId.CactusMinion2,
            MinionItemId.CactusMinion5,
            MinionItemId.CarrotMinion1,
            MinionItemId.CarrotMinion2,
            MinionItemId.CarrotMinion6,
            MinionItemId.CarrotMinion10,
            MinionItemId.CaveSpiderMinion2,
            MinionItemId.CaveSpiderMinion3,
            MinionItemId.ChickenMinion6,
            MinionItemId.ChickenMinion7,
            MinionItemId.ClayMinion2,
            MinionItemId.ClayMinion3,
            MinionItemId.ClayMinion4,
            MinionItemId.CocoaBeansMinion6,
            MinionItemId.CowMinion6,
            MinionItemId.CreeperMinion2,
            MinionItemId.CreeperMinion3,
            MinionItemId.DarkOakMinion3,
            MinionItemId.EmeraldMinion2,
            MinionItemId.EndStoneMinion1,
            MinionItemId.EndStoneMinion2,
            MinionItemId.EndStoneMinion3,
            MinionItemId.EndStoneMinion7,
            MinionItemId.EndermanMinion9,
            MinionItemId.FlowerMinion2,
            MinionItemId.FlowerMinion4,
            MinionItemId.FlowerMinion5,
            MinionItemId.FlowerMinion6,
            MinionItemId.FlowerMinion7,
            MinionItemId.FlowerMinion8,
            MinionItemId.FlowerMinion9,
            MinionItemId.FlowerMinion10,
            MinionItemId.GhastMinion1,
            MinionItemId.GhastMinion4,
            MinionItemId.GhastMinion5,
            MinionItemId.GlowstoneMinion2,
            MinionItemId.GlowstoneMinion3,
            MinionItemId.GlowstoneMinion4,
            MinionItemId.GlowstoneMinion5,
            MinionItemId.GlowstoneMinion7,
            MinionItemId.GravelMinion1,
            MinionItemId.GravelMinion2,
            MinionItemId.HardStoneMinion3,
            MinionItemId.HardStoneMinion7,
            MinionItemId.HardStoneMinion10,
            MinionItemId.IceMinion2,
            MinionItemId.IceMinion3,
            MinionItemId.IceMinion6,
            MinionItemId.InfernoMinion4,
            MinionItemId.InfernoMinion7,
            MinionItemId.InfernoMinion9,
            MinionItemId.InfernoMinion10,
            MinionItemId.IronMinion2,
            MinionItemId.MagmaCubeMinion2,
            MinionItemId.MagmaCubeMinion4,
            MinionItemId.MelonMinion6,
            MinionItemId.MushroomMinion3,
            MinionItemId.MyceliumMinion6,
            MinionItemId.MyceliumMinion11,
            MinionItemId.NetherWartMinion2,
            MinionItemId.NetherWartMinion6,
            MinionItemId.OakMinion2,
            MinionItemId.ObsidianMinion5,
            MinionItemId.PigMinion2,
            MinionItemId.PotatoMinion2,
            MinionItemId.PumpkinMinion2,
            MinionItemId.PumpkinMinion5,
            MinionItemId.QuartzMinion1,
            MinionItemId.RabbitMinion3,
            MinionItemId.RedSandMinion4,
            MinionItemId.RedSandMinion9,
            MinionItemId.RedstoneMinion2,
            MinionItemId.RevenantMinion2,
            MinionItemId.RevenantMinion11,
            MinionItemId.SandMinion2,
            MinionItemId.SandMinion3,
            MinionItemId.SheepMinion2,
            MinionItemId.SkeletonMinion2,
            MinionItemId.SlimeMinion2,
            MinionItemId.SnowMinion4,
            MinionItemId.SnowMinion6,
            MinionItemId.SnowMinion10,
            MinionItemId.SpruceMinion5,
            MinionItemId.SugarCaneMinion1,
            MinionItemId.SugarCaneMinion8,
            MinionItemId.TarantulaMinion8,
            MinionItemId.TarantulaMinion10,
            MinionItemId.VampireMinion2,
            MinionItemId.VampireMinion3,
            MinionItemId.VampireMinion8,
            MinionItemId.VampireMinion9,
            MinionItemId.VoidlingMinion6,
            MinionItemId.VoidlingMinion10,
            MinionItemId.ZombieMinion1,
            MinionItemId.InfernoFuelGlowstoneDust,
            MinionItemId.InfernoFuelNetherWart,
            MinionItemId.InfernoFuelBlazeRod,
            MinionItemId.InfernoHeavyMagmaCream,
            MinionItemId.InfernoHeavyGlowstoneDust,
            MinionItemId.InfernoHeavyNetherWart,
            MinionItemId.InfernoHeavyBlazeRod,
            MinionItemId.InfernoHeavyCrudeGabagool,
            MinionItemId.InfernoHypergolicMagmaCream,
            MinionItemId.InfernoHypergolicGlowstoneDust,
            MinionItemId.InfernoHypergolicNetherWart,
            MinionItemId.InfernoHypergolicBlazeRod,
            CosmeticItemId.Bookcase,
            CosmeticItemId.CoconutDrink,
            CosmeticItemId.EggPaintingStation,
            CosmeticItemId.ExtraLargeNutcracker,
            CosmeticItemId.Fireplace,
            KnownPetSkinId.HeonzoDoggoWolfSkin,
            CosmeticItemId.MediumShelves,
            CosmeticItemId.TenthAnniversaryPodiumBestMemeRunnerUp,
            CosmeticItemId.TenthAnniversaryPodiumBestMemeSecond,
            CosmeticItemId.TenthAnniversaryPodiumBestMemeThird,
            CosmeticItemId.TenthAnniversaryPodiumFanArtFirst,
            CosmeticItemId.TenthAnniversaryPodiumFanArtRunnerUp,
            CosmeticItemId.TenthAnniversaryPodiumFanArtSecond,
            CosmeticItemId.TenthAnniversaryPodiumFanArtThird,
            CosmeticItemId.TenthAnniversaryPodiumFavoriteMemoryFirst,
            CosmeticItemId.TenthAnniversaryPodiumFavoriteMemoryRunnerUp,
            CosmeticItemId.TenthAnniversaryPodiumFavoriteMemorySecond,
            CosmeticItemId.TenthAnniversaryPodiumFavoriteMemoryThird,
            CosmeticItemId.TenthAnniversaryPodiumNoteBlockFirst,
            CosmeticItemId.TenthAnniversaryPodiumNoteBlockRunnerUp,
            CosmeticItemId.TenthAnniversaryPodiumNoteBlockSecond,
            CosmeticItemId.TenthAnniversaryPodiumNoteBlockThird,
            CosmeticItemId.Totem,
            CosmeticItemId.WeaponRackPlus,
            CosmeticItemId.WishingWell,
            GemstoneItemId.RoughOpalGemstone,
            GemstoneItemId.FlawedOpalGemstone,
            GemstoneItemId.FineOpalGemstone,
            GemstoneItemId.RoughAquamarineGemstone,
            GemstoneItemId.FlawedAquamarineGemstone,
            GemstoneItemId.FlawedOnyxGemstone,
            ArmorItemId.PerfectChestplateTier2,
            ArmorItemId.PerfectChestplateTier3,
            ArmorItemId.PerfectHelmetTier4,
            ArmorItemId.PerfectLeggingsTier4,
            ArmorItemId.PerfectChestplateTier5,
            ArmorItemId.PerfectLeggingsTier5,
            ArmorItemId.PerfectBootsTier5,
            ArmorItemId.PerfectChestplateTier6,
            ArmorItemId.PerfectLeggingsTier6,
            ArmorItemId.PerfectHelmetTier7,
            ArmorItemId.PerfectBootsTier7,
            ArmorItemId.PerfectHelmetTier8,
            ArmorItemId.PerfectChestplateTier8,
            ArmorItemId.PerfectLeggingsTier8,
            ArmorItemId.PerfectBootsTier8,
            ArmorItemId.PerfectHelmetTier9,
            ArmorItemId.PerfectChestplateTier9,
            ArmorItemId.PerfectLeggingsTier9,
            ArmorItemId.PerfectBootsTier9,
            ArmorItemId.PerfectHelmetTier10,
            ArmorItemId.PerfectLeggingsTier10,
            ArmorItemId.PerfectBootsTier10,
            ArmorItemId.PerfectHelmetTier11,
            ArmorItemId.PerfectChestplateTier11,
            ArmorItemId.PerfectLeggingsTier11,
            ArmorItemId.PerfectBootsTier11,
            MiscItemId.EnchantedBookBundleCounterStrike,
            MiscItemId.PortableWoolWeaver,
            VanillaItemId.ClayBrick,
            WeaponItemId.Cutlass,
            KnownDyeId.BrickRedDye,
            KnownDyeId.ByzantiumDye,
            KnownDyeId.CeladonDye,
            KnownDyeId.EmeraldDye,
            KnownDyeId.FlameDye,
            KnownDyeId.FossilDye,
            KnownDyeId.JadeDye,
            KnownDyeId.MatchaDye,
            KnownDyeId.MidnightDye,
            KnownDyeId.PeltDye,
            KnownDyeId.PeriwinkleDye,
            KnownDyeId.SangriaDye,
            MiscItemId.TravelScrollToDarkAuction,
            MiscItemId.TravelScrollToMushroomIsland,
            MiscItemId.TravelScrollToTheGoldMine,
            MiscItemId.TravelScrollToTheCrystalHollows,
            MiscItemId.TravelScrollToArachnesSanctuary,
            MiscItemId.TravelScrollToTheSmolderingTomb,
            MiscItemId.TravelScrollToTheDwarvenBaseCamp,
            KnownPetSkinId.BullheadMegalodon,
            CosmeticItemId.HamBarnSkin,
            CosmeticItemId.CountsManorBarnSkin,
            KnownPetSkinId.BrownSheepSkin,
            KnownPetSkinId.AquaSheepSkin,
            DisplayItemId.Thunderstorm,
            DisplayItemId.FallenStarChestplate,
            DisplayItemId.FallenStarLeggings,
            DisplayItemId.FallenStarBoots,
            DisplayItemId.CryptSkullKey,
            DisplayItemId.GoldenKey,
            DisplayItemId.TheWatchersHead,
            DisplayItemId.PopUpWallAbility,
            DisplayItemId.AmberCrystal,
            VanillaItemId.ArmorStand,
            VanillaItemId.BookAndQuill,
            VanillaItemId.FireworkStar,
            VanillaItemId.VanillaNetherStar,
            VanillaItemId.WitherSkeletonSkull,
            VanillaItemId.ZombieSkull,
            BuggedItemId.NullMap3,
            VanillaItemId.SpawnEgg,
            AccessoryItemId.JunkTalisman,
            CosmeticItemId.BunnyCabinet,
            CosmeticItemId.BunnyTV,
            CosmeticItemId.SpringBarnSkin,
            MiscItemId.BronzeBowl,
            MiscItemId.BronzeShipEngine,
            MiscItemId.BronzeShipHelm,
            MiscItemId.BronzeShipHull,
            MiscItemId.OctopusTendril,
            MiscItemId.OldLeatherBoot,
            MiscItemId.RustyCoin,
            MiscItemId.SeveredPincer,
            MiscItemId.SingedPowder,
            MiscItemId.TravelScrollToTheBayou,
            KnownPetItem.BurntTexts,
            KnownPetItem.ScuttlerShell,
            MiscItemId.BrokenRadar,
            KnownRodPartId.ChumSinker,
            KnownRodPartId.FestiveSinker,
            KnownRodPartId.PrismarineSinker,
            KnownRodPartId.TitanLine,
            MiscItemId.HalfEatenMushroom,
            MiscItemId.TitanoboaShed,
            MiscItemId.BayouWaterOrb,
            MiscItemId.HotspotWaterOrb,
            CosmeticItemId.ButterflyBarnSkin,
            CosmeticItemId.FlowerPotBarnSkin,
            MiscItemId.AlligatorSkin,
            MiscItemId.BlueRing,
            MiscItemId.ChainOfTheEndTimes,
            MiscItemId.GoldBottleCap,
            MiscItemId.PortalToTheBackwaterBayou,
            MiscItemId.TornCloth,
            KnownDyeId.OasisDye,
            KnownDyeId.SunsetDye,
            KnownPetSkinId.MyceliyumBurgerMooshroomCowSkin,
            KnownPetSkinId.HollowRockSkin,
            KnownPetSkinId.IceFairySubzeroWispSkin
        )
    }
}