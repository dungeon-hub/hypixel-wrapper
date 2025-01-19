package net.dungeonhub.hypixel.entities

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
    val secrets: Int?,
    val raw: JsonObject
) {
    val requiredExperience = listOf(
        50, 125, 235, 395, 625, 955, 1425, 2095, 3045, 4385, 6275, 8940, 12700,
        17960, 25340, 35640, 50040, 70040, 97640, 135640, 188140, 259640, 356640, 488640, 668640, 911640, 1239640,
        1684640, 2284640, 3084640, 4149640, 5559640, 7459640, 9959640, 13259640, 17559640, 23159640, 30359640,
        39559640, 51559640, 66559640, 85559640, 109559640, 139559640, 177559640, 225559640, 285559640, 360559640,
        453559640, 569809640
    )

    fun levelFromExperience(experience: Double): Int {
        for (i in requiredExperience.indices) {
            if (requiredExperience[i] > (experience)) return i
        }

        // 50 and everything higher is returned as 50
        return 50
    }

    val classAverage = CatacombsClass.entries.sumOf {
        levelFromExperience(classExperience[it] ?: 0.0)
    }.toDouble() / CatacombsClass.entries.size

    val catacombsExperience = dungeonTypes[KnownDungeonType.Catacombs]?.experience
    val catacombsLevel: Int?
        get() {
            if(catacombsExperience == null) {
                return null
            }

            return levelFromExperience(catacombsExperience)
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
        getAsJsonPrimitiveOrNull("selected_dungeon_class")?.asString?.let(CatacombsClass::fromApiName),
        getAsJsonObjectOrNull("daily_runs"),
        getAsJsonObjectOrNull("treasures"),
        getAsJsonObjectOrNull("dungeon_hub_race_settings"),
        getAsJsonPrimitiveOrNull("last_dungeon_run")?.asString,
        getAsJsonPrimitiveOrNull("secrets")?.asInt,
        this
    )
}