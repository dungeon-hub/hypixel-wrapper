package net.dungeonhub.hypixel.entities

import com.google.gson.JsonObject
import net.dungeonhub.provider.getAsJsonArrayOrNull
import net.dungeonhub.provider.getAsJsonObjectOrNull
import net.dungeonhub.provider.getAsJsonPrimitiveOrNull

//TODO check what can we not-null
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
)

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