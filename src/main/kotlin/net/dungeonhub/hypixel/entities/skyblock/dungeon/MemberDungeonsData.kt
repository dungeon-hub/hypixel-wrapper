package net.dungeonhub.hypixel.entities.skyblock.dungeon

import com.google.gson.JsonObject
import net.dungeonhub.provider.getAsJsonArrayOrNull
import net.dungeonhub.provider.getAsJsonObjectOrNull
import net.dungeonhub.provider.getAsJsonPrimitiveOrNull

// TODO check what can be not-null
data class MemberDungeonsData(
    val dungeonTypes: Map<DungeonType, DungeonData>,
    val classExperience: Map<CatacombsClass, Double>,
    val dungeonsBlahBlah: List<String>?,
    val selectedClass: CatacombsClass?,
    val dailyRuns: JsonObject?,
    val treasures: JsonObject?,
    val raceSettings: JsonObject?,
    val lastRun: String?,
    val secrets: Int?,
    val raw: JsonObject
) {
    val classAverage = CatacombsClass.entries.sumOf {
        levelFromExperience(classExperience[it] ?: 0.0)
    }.toDouble() / CatacombsClass.entries.size

    val catacombsExperience = dungeonTypes[KnownDungeonType.Catacombs]?.experience
    val catacombsLevel: Int?
        get() {
            if (catacombsExperience == null) {
                return null
            }

            return levelFromExperience(catacombsExperience)
        }

    companion object {
        const val MAX_LEVEL = 500

        val requiredExperience = listOf<Long>(
            50,
            75,
            110,
            160,
            230,
            330,
            470,
            670,
            950,
            1_340,
            1_890,
            2_665,
            3_760,
            5_260,
            7_380,
            10_300,
            14_400,
            20_000,
            27_600,
            38_000,
            52_500,
            71_500,
            97_000,
            132_000,
            180_000,
            243_000,
            328_000,
            445_000,
            600_000,
            800_000,
            1_065_000,
            1_410_000,
            1_900_000,
            2_500_000,
            3_300_000,
            4_300_000,
            5_600_000,
            7_200_000,
            9_200_000,
            12_000_000,
            15_000_000,
            19_000_000,
            24_000_000,
            30_000_000,
            38_000_000,
            48_000_000,
            60_000_000,
            75_000_000,
            93_000_000,
            116_250_000,
            200_000_000
        )

        fun getRequiredExperience(level: Int): Long {
            if (level > requiredExperience.size) {
                return getRequiredExperience(requiredExperience.size) + requiredExperience.last() *
                        (level - requiredExperience.size)
            }

            return requiredExperience.take(level).sum()
        }

        fun levelFromExperience(experience: Double): Int {
            for (i in 0..MAX_LEVEL) {
                if (getRequiredExperience(i) > experience) return i - 1
            }

            // Return the max level that is parsed -> No one has level 500, so it will calculate the max level
            return MAX_LEVEL
        }
    }
}

fun JsonObject.toDungeonsData(): MemberDungeonsData {
    return MemberDungeonsData(
        getAsJsonObjectOrNull("dungeon_types")?.entrySet()?.associate {
            KnownDungeonType.fromApiName(it.key) to it.value.asJsonObject.toDungeonData()
        } ?: emptyMap(),
        getAsJsonObjectOrNull("player_classes")?.entrySet()?.associate {
            CatacombsClass.fromApiName(it.key)!! to
                    (it.value.asJsonObject.getAsJsonPrimitiveOrNull("experience")?.asDouble ?: 0.0)
        } ?: emptyMap(),
        getAsJsonArrayOrNull("dungeons_blah_blah")?.asList()?.map { it.asString },
        getAsJsonPrimitiveOrNull("selected_dungeon_class")?.asString?.let(CatacombsClass.Companion::fromApiName),
        getAsJsonObjectOrNull("daily_runs"),
        getAsJsonObjectOrNull("treasures"),
        getAsJsonObjectOrNull("dungeon_hub_race_settings"),
        getAsJsonPrimitiveOrNull("last_dungeon_run")?.asString,
        getAsJsonPrimitiveOrNull("secrets")?.asInt,
        this
    )
}