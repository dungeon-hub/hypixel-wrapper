package net.dungeonhub.hypixel.entities.skyblock

import net.dungeonhub.detekt.NotMagic

enum class KnownSkill(
    override val apiName: String,
    val maxLevel: Int,
    val requiredExperience: List<Int> = @NotMagic listOf(
        50,
        175,
        375,
        675,
        1_175,
        1_925,
        2_925,
        4_425,
        6_425,
        9_925,
        14_925,
        22_425,
        32_425,
        47_425,
        67_425,
        97_425,
        147_425,
        222_425,
        322_425,
        522_425,
        822_425,
        1_222_425,
        1_722_425,
        2_322_425,
        3_022_425,
        3_822_425,
        4_722_425,
        5_722_425,
        6_822_425,
        8_022_425,
        9_322_425,
        10_722_425,
        12_222_425,
        13_822_425,
        15_522_425,
        17_322_425,
        19_222_425,
        21_222_425,
        23_322_425,
        25_522_425,
        27_822_425,
        30_222_425,
        32_722_425,
        35_322_425,
        38_072_425,
        40_972_425,
        44_072_425,
        47_472_425,
        51_172_425,
        55_172_425,
        59_472_425,
        64_072_425,
        68_972_425,
        74_172_425,
        79_672_425,
        85_472_425,
        91_572_425,
        97_972_425,
        104_672_425,
        111_672_425
    ),
    val cosmetic: Boolean = false
) : Skill {
    Combat("SKILL_COMBAT", 60),
    Carpentry("SKILL_CARPENTRY", 50),
    Foraging("SKILL_FORAGING", 50),
    Taming("SKILL_TAMING", 60),
    Enchanting("SKILL_ENCHANTING", 60),
    Farming("SKILL_FARMING", 60),
    Mining("SKILL_MINING", 60),
    Alchemy("SKILL_ALCHEMY", 50),
    Fishing("SKILL_FISHING", 50),
    Social(
        "SKILL_SOCIAL",
        25,
        listOf(
            50,
            150,
            300,
            550,
            1_050,
            1_800,
            2_800,
            4_050,
            5_550,
            7_550,
            10_050,
            13_050,
            16_800,
            21_300,
            27_300,
            35_300,
            45_300,
            57_800,
            72_800,
            92_800,
            117_800,
            147_800,
            182_800,
            222_800,
            272_800
        ),
        true
    ),
    Runecrafting(
        "SKILL_RUNECRAFTING",
        25,
        listOf(
            50,
            150,
            275,
            435,
            635,
            885,
            1_200,
            1_600,
            2_100,
            2_725,
            3_150,
            4_510,
            5_760,
            7_325,
            9_325,
            11_825,
            14_950,
            18_950,
            23_950,
            30_200,
            38_050,
            47_850,
            60_100,
            75_400,
            94_500
        ),
        true
    );

    enum class DeprecatedSkill(override val apiName: String) : Skill {
        Dungeoneering("SKILL_DUNGEONEERING")
    }

    fun calculateLevel(experience: Double): Int {
        for (i in requiredExperience.indices) {
            if (i > maxLevel) return maxLevel

            if (requiredExperience[i] > experience) return i
        }

        return maxLevel
    }

    class UnknownSkill(override val apiName: String) : Skill

    companion object {
        fun fromApiName(apiName: String): Skill {
            return entries.firstOrNull { it.apiName == apiName }
                ?: DeprecatedSkill.entries.firstOrNull {
                    it.apiName == apiName
                } ?: UnknownSkill(apiName)
        }
    }
}