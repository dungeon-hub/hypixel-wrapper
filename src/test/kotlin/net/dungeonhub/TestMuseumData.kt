package net.dungeonhub

import net.dungeonhub.service.TestHelper
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.Test

class TestMuseumData {
    @Test
    fun testMuseumData() {
        assertDoesNotThrow {
            TestHelper.readAllMuseumData()
        }
    }
}