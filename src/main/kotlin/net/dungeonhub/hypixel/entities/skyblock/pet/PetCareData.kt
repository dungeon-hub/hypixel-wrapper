package net.dungeonhub.hypixel.entities.skyblock.pet

import com.google.gson.JsonObject
import net.dungeonhub.provider.getAsJsonArrayOrNull
import net.dungeonhub.provider.getAsJsonPrimitiveOrNull

class PetCareData(
    val coinsSpent: Double?,
    val petsSacrificed: List<PetType>
)

fun JsonObject.toPetCareData(): PetCareData {
    return PetCareData(
        getAsJsonPrimitiveOrNull("coins_spent")?.asDouble,
        getAsJsonArrayOrNull("pet_types_sacrificed")?.asList()?.map { KnownPetType.fromApiName(it.asString) }
            ?: listOf()
    )
}