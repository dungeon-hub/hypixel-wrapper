package net.dungeonhub

import net.dungeonhub.hypixel.entities.bingo.KnownBingoEventType
import net.dungeonhub.service.TestHelper
import org.junit.jupiter.api.assertDoesNotThrow
import java.util.*
import kotlin.test.*
import kotlin.time.Duration
import kotlin.time.toKotlinDuration

class TestBingoData {
    @Test
    fun testBingoPointCalculation() {
        val bingoPoints = mapOf(
            UUID.fromString("92d940ad-10e8-4d9d-a795-adcf5fd6b0c6") to 3673
        )

        for (bingoData in TestHelper.readAllBingoData()) {
            if (bingoPoints.contains(bingoData.player)) {
                assertEquals(bingoPoints[bingoData.player], bingoData.totalPoints)
            }
        }
    }

    @Test
    fun testBingoDurationParsing() {
        for (eventType in KnownBingoEventType.entries) {
            assertDoesNotThrow { eventType.duration }
            assertNotNull(eventType.duration)
            assertIs<Duration>(eventType.duration)
            assertNotEquals(Duration.ZERO, eventType.duration)
        }
    }

    @Test
    fun testBingoEventParsing() {
        TestHelper.runParallel {
            TestHelper.readAllBingoEvents().forEach { event ->
                assertTrue(event.goals.isNotEmpty())
                assertIsNot<KnownBingoEventType.UnknownBingoEventType>(event.type)
                assertIs<KnownBingoEventType>(event.type)

                assertDoesNotThrow { event.month }
                assertDoesNotThrow { event.year }

                assertEquals(event.type.duration, java.time.Duration.between(event.start, event.end).toKotlinDuration())
            }
        }
    }
}