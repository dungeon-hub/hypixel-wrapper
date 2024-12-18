package net.dungeonhub

import com.google.gson.JsonArray
import net.dungeonhub.hypixel.entities.toSkyblockProfile
import net.dungeonhub.provider.GsonProvider
import net.dungeonhub.service.TestHelper
import java.nio.charset.StandardCharsets
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class TestSkyblockProfile {
    @Test
    fun testToSkyblockProfile() {
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

        assertEquals(profiles.size, 2)
    }
}