package net.dungeonhub.hypixel.entities

enum class KnownSkill(
    override val apiName: String, val maxLevel: Int, val requiredExperience: List<Int> = listOf(
        50,
        175,
        375,
        675,
        1175,
        1925,
        2925,
        4425,
        6425,
        9925,
        14925,
        22425,
        32425,
        47425,
        67425,
        97425,
        147425,
        222425,
        322425,
        522425,
        822425,
        1222425,
        1722425,
        2322425,
        3022425,
        3822425,
        4722425,
        5722425,
        6822425,
        8022425,
        9322425,
        10722425,
        12222425,
        13822425,
        15522425,
        17322425,
        19222425,
        21222425,
        23322425,
        25522425,
        27822425,
        30222425,
        32722425,
        35322425,
        38072425,
        40972425,
        44072425,
        47472425,
        51172425,
        55172425,
        59472425,
        64072425,
        68972425,
        74172425,
        79672425,
        85472425,
        91572425,
        97972425,
        104672425,
        111672425
    ), val cosmetic: Boolean = false
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
            1050,
            1800,
            2800,
            4050,
            5550,
            7550,
            10050,
            13050,
            16800,
            21300,
            27300,
            35300,
            45300,
            57800,
            72800,
            92800,
            117800,
            147800,
            182800,
            222800,
            272800
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
            1200,
            1600,
            2100,
            2725,
            3150,
            4510,
            5760,
            7325,
            9325,
            11825,
            14950,
            18950,
            23950,
            30200,
            38050,
            47850,
            60100,
            75400,
            94500
        ),
        true
    );

    fun calculateLevel(experience: Double): Int {
        for (i in requiredExperience.indices) {
            if(i > maxLevel) return maxLevel

            if (requiredExperience[i] > (experience)) return i
        }

        return maxLevel
    }

    class UnknownSkill(override val apiName: String) : Skill

    companion object {
        fun fromApiName(apiName: String): Skill {
            return entries.firstOrNull { it.apiName == apiName }
                ?: UnknownSkill(apiName)
        }
    }
}