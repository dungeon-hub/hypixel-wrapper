package net.dungeonhub

import net.dungeonhub.service.TestHelper
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals

class TestBingoData {
    @Test
    fun testBingoPointCalculation() {
        val bingoPoints = mapOf(
            UUID.fromString("92d940ad-10e8-4d9d-a795-adcf5fd6b0c6") to 3673
        )

        for(bingoData in TestHelper.readAllBingoData()) {
            if(bingoPoints.contains(bingoData.player)) {
                assertEquals(bingoPoints[bingoData.player], bingoData.totalPoints)
            }
        }
    }
}