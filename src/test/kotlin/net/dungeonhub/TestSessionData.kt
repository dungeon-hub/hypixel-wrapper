package net.dungeonhub

import net.dungeonhub.service.TestHelper
import kotlin.test.Test
import kotlin.test.assertNotNull

class TestSessionData {
    @Test
    fun testSessionDataParsing() {
        for (sessionData in TestHelper.readAllPlayerSessions()) {
            assertNotNull(sessionData.uuid)
            assertNotNull(sessionData.online)
        }
    }
}