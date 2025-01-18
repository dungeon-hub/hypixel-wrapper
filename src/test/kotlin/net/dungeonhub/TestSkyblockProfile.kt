package net.dungeonhub

import com.google.gson.JsonArray
import net.dungeonhub.hypixel.client.RestApiClient
import net.dungeonhub.hypixel.entities.*
import net.dungeonhub.hypixel.entities.inventory.GemstoneQuality
import net.dungeonhub.hypixel.entities.inventory.ItemStack
import net.dungeonhub.hypixel.entities.inventory.SkyblockItem
import net.dungeonhub.provider.GsonProvider
import net.dungeonhub.service.TestHelper
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

                fullProfile.members.filterIsInstance<CurrentMember>().mapNotNull { it.fairySoulData }.forEach {
                    assertEquals(it.unspentSouls, it.totalCollected - it.totalExchanged)
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
        assertNotNull(member.inventory!!.enderChestContent)
        assertNotNull(member.inventory!!.armor)
        assertNotNull(member.inventory!!.equipment)
        assertNotNull(member.inventory!!.personalVault)
        assertNotNull(member.inventory!!.equippedWardrobeSlot)
        assertNotNull(member.inventory!!.wardrobeContents)

        assertEquals(
            "Heroic Hyperion ✪✪✪✪✪➌",
            member.inventory!!.inventoryContents!!.items.filterNotNull().first().rawName
        )
        assertEquals("Abiphone XII Mega", member.inventory!!.inventoryContents!!.items.filterNotNull().last().rawName)
    }

    @Test
    fun testItemNameParsing() {
        for (fullProfiles in TestHelper.readAllSkyblockProfiles()) {
            for (fullProfile in fullProfiles) {
                fullProfile.members.filterIsInstance<CurrentMember>().forEach { member ->
                    if (member.inventory != null) {
                        val inventory = member.inventory!!

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

    fun checkItems(items: List<ItemStack>) {
        for (item in items) {
            assertNotNull(item.tag)
            assertNotNull(item.name)
            assertNotNull(item.rawName)
            assertNotNull(item.extraAttributes)

            if (item is SkyblockItem) {
                assertNotNull(item.id)
                assertTrue(item.runes.isEmpty() || item.runes.size == 1)
                assertTrue(item.gems == null || item.gems!!.appliedGemstones.values.all {
                    GemstoneQuality.entries.contains(
                        it.gemstoneQuality
                    )
                })
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
                        val inventoryContent = member.inventory!!.inventoryContents?.items!!

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
    fun testNoUnknownDataTypes() {
        for (skyblockProfiles in TestHelper.readAllSkyblockProfiles()) {
            for (skyblockProfile in skyblockProfiles) {
                for (member in skyblockProfile.members) {
                    member.slayer?.slayerProgress?.keys?.forEach {
                        assertIsNot<KnownSlayerType.UnknownSlayerType>(it)
                    }

                    assertIsNot<KnownSlayerType.UnknownSlayerType>(member.slayer?.activeSlayerQuest?.type)

                    member.playerData?.experience?.keys?.forEach {
                        //ignore dungeoneering -> it's an old entry I guess
                        if(it is KnownSkill.UnknownSkill && it.apiName == "SKILL_DUNGEONEERING") {
                            return@forEach
                        }

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
                    }
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
    }
}