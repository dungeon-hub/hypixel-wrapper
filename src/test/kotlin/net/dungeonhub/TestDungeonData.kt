package net.dungeonhub

import net.dungeonhub.hypixel.entities.skyblock.dungeon.MemberDungeonsData
import kotlin.test.Test
import kotlin.test.assertEquals

class TestDungeonData {
    @Test
    fun testRequiredExperienceCalculation() {
        val requiredExperience = listOf<Long>(
            50, 125, 235, 395, 625, 955, 1425, 2095, 3045, 4385, 6275, 8940, 12700,
            17960, 25340, 35640, 50040, 70040, 97640, 135640, 188140, 259640, 356640, 488640, 668640, 911640, 1239640,
            1684640, 2284640, 3084640, 4149640, 5559640, 7459640, 9959640, 13259640, 17559640, 23159640, 30359640,
            39559640, 51559640, 66559640, 85559640, 109559640, 139559640, 177559640, 225559640, 285559640, 360559640,
            453559640, 569809640, 769809640, 969809640, 1169809640, 1369809640, 1569809640, 1769809640
        )
        
        for((index, experience) in requiredExperience.withIndex()) {
            assertEquals(experience, MemberDungeonsData.getRequiredExperience(index + 1))
        }
    }

    @Test
    fun testLevelCalculation() {
        val experienceToLevel = mapOf<Double, Int>(
            0.0 to 0,
            0.1 to 0,
            51.23 to 1,
            12700.0 to 13,
            12699.9 to 12,
            12700.0 to 13,
            1569809641.0 to 55,
            45104.28232943482 to 16
        )

        for((experience, level) in experienceToLevel) {
            assertEquals(level, MemberDungeonsData.levelFromExperience(experience))
        }
    }
}