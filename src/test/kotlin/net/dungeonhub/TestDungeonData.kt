package net.dungeonhub

import net.dungeonhub.hypixel.entities.skyblock.dungeon.MemberDungeonsData
import kotlin.test.Test
import kotlin.test.assertEquals

class TestDungeonData {
    @Test
    fun testRequiredExperienceCalculation() {
        val requiredExperience = listOf<Long>(
            50,
            125,
            235,
            395,
            625,
            955,
            1_425,
            2_095,
            3_045,
            4_385,
            6_275,
            8_940,
            12_700,
            17_960,
            25_340,
            35_640,
            50_040,
            70_040,
            97_640,
            135_640,
            188_140,
            259_640,
            356_640,
            488_640,
            668_640,
            911_640,
            1_239_640,
            1_684_640,
            2_284_640,
            3_084_640,
            4_149_640,
            5_559_640,
            7_459_640,
            9_959_640,
            13_259_640,
            17_559_640,
            23_159_640,
            30_359_640,
            39_559_640,
            51_559_640,
            66_559_640,
            85_559_640,
            109_559_640,
            139_559_640,
            177_559_640,
            225_559_640,
            285_559_640,
            360_559_640,
            453_559_640,
            569_809_640,
            769_809_640,
            969_809_640,
            1_169_809_640,
            1_369_809_640,
            1_569_809_640,
            1_769_809_640
        )

        for ((index, experience) in requiredExperience.withIndex()) {
            assertEquals(experience, MemberDungeonsData.getRequiredExperience(index + 1))
        }
    }

    @Test
    fun testLevelCalculation() {
        val experienceToLevel = mapOf<Double, Int>(
            0.0 to 0,
            0.1 to 0,
            51.23 to 1,
            12_700.0 to 13,
            12_699.9 to 12,
            12_700.0 to 13,
            1_569_809_641.0 to 55,
            45_104.28232943482 to 16
        )

        for ((experience, level) in experienceToLevel) {
            assertEquals(level, MemberDungeonsData.levelFromExperience(experience))
        }
    }
}