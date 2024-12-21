package net.dungeonhub

import com.google.gson.JsonArray
import net.dungeonhub.hypixel.entities.*
import net.dungeonhub.provider.GsonProvider
import net.dungeonhub.service.TestHelper
import java.time.Instant
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

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
        val fullProfilesJson = TestHelper.readFile("full_skyblock_profiles.json")

        val fullProfiles = GsonProvider.gson.fromJson(fullProfilesJson, JsonArray::class.java).asList()

        val parsedProfiles = fullProfiles.map { it.toSkyblockProfile() }
        assertEquals(4, parsedProfiles.size)
        assertEquals(3, parsedProfiles.filter { it.gameMode == ProfileGameMode.Default }.size)
        assertEquals(0, parsedProfiles.filter { it.gameMode == ProfileGameMode.Ironman }.size)
        assertEquals(1, parsedProfiles.filter { it.gameMode == ProfileGameMode.Bingo }.size)
    }

    @Test
    fun testFullProfile() {
        val fullProfilesJson = TestHelper.readFile("full_skyblock_profiles.json")

        val fullProfiles = GsonProvider.gson.fromJson(fullProfilesJson, JsonArray::class.java).asList()

        for(fullProfileJson in fullProfiles) {
            val fullProfile = fullProfileJson.toSkyblockProfile()

            assertNotNull(fullProfile)

            assertNotNull(fullProfile.cuteName)
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

        val fullProfilesJson = TestHelper.readFile("full_skyblock_profiles.json")

        val fullProfiles = GsonProvider.gson.fromJson(fullProfilesJson, JsonArray::class.java).asList()

        for(fullProfileJson in fullProfiles) {
            val fullProfile = fullProfileJson.toSkyblockProfile()

            assertEquals(currentMembers[fullProfile.profileId.toString()], fullProfile.currentMembers.size)
            assertEquals(pastMembers[fullProfile.profileId.toString()], fullProfile.members.filterIsInstance<PastMember>().size)
            assertEquals(pendingMembers[fullProfile.profileId.toString()], fullProfile.members.filterIsInstance<PendingMember>().size)
        }
    }
}