package net.dungeonhub.hypixel.entities.skyblock.rift

import com.google.gson.JsonObject
import net.dungeonhub.hypixel.entities.skyblock.pet.Pet
import net.dungeonhub.hypixel.entities.skyblock.pet.toPet
import net.dungeonhub.provider.getAsJsonArrayOrNull
import net.dungeonhub.provider.getAsJsonObjectOrNull
import net.dungeonhub.provider.getAsJsonPrimitiveOrNull

class DeadCatData(
    val talkedToJacquelle: Boolean,
    val foundCats: List<String>,
    val pickedUpDetector: Boolean,
    val montezuma: Pet?,
    val unlockedPet: Boolean
)

fun JsonObject.toDeadCatData(): DeadCatData {
    return DeadCatData(
        getAsJsonPrimitiveOrNull("talked_to_jacquelle")?.asBoolean ?: false,
        getAsJsonArrayOrNull("found_cats")?.map { it.asString } ?: listOf(),
        getAsJsonPrimitiveOrNull("picked_up_detector")?.asBoolean ?: false,
        getAsJsonObjectOrNull("montezuma")?.toPet(),
        getAsJsonPrimitiveOrNull("unlocked_pet")?.asBoolean ?: false
    )
}