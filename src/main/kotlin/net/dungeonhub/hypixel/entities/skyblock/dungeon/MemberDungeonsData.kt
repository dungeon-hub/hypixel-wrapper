package net.dungeonhub.hypixel.entities.skyblock.dungeon

import com.google.gson.JsonObject
import net.dungeonhub.provider.getAsJsonArrayOrNull
import net.dungeonhub.provider.getAsJsonObjectOrNull
import net.dungeonhub.provider.getAsJsonPrimitiveOrNull

//TODO check what can be not-null
class MemberDungeonsData(
    val dungeonTypes: Map<DungeonType, DungeonData>,
    val classExperience: Map<CatacombsClass, Double>,
    val dungeonsBlahBlah: List<String>?,
    val selectedClass: CatacombsClass?,
    val dailyRuns: JsonObject?,
    val treasures: JsonObject?,
    val raceSettings: JsonObject?,
    val lastRun: String?,
    val secrets: Int?
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
            1340,
            1890,
            2665,
            3760,
            5260,
            7380,
            10300,
            14400,
            20000,
            27600,
            38000,
            52500,
            71500,
            97000,
            132000,
            180000,
            243000,
            328000,
            445000,
            600000,
            800000,
            1065000,
            1410000,
            1900000,
            2500000,
            3300000,
            4300000,
            5600000,
            7200000,
            9200000,
            12000000,
            15000000,
            19000000,
            24000000,
            30000000,
            38000000,
            48000000,
            60000000,
            75000000,
            93000000,
            116250000,
            200000000
        )

        fun getRequiredExperience(level: Int): Long {
            if(level > requiredExperience.size) {
                return getRequiredExperience(requiredExperience.size) + requiredExperience.last() * (level - requiredExperience.size)
            }

            return requiredExperience.take(level).sum()
        }

        fun levelFromExperience(experience: Double): Int {
            for (i in 0..500) {
                if (getRequiredExperience(i) > (experience)) return i-1
            }

            // 50 and everything higher is returned as 50
            return 500
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
        getAsJsonPrimitiveOrNull("secrets")?.asInt
    )
}