package net.dungeonhub.hypixel.entities.skyblock.pet

import com.google.gson.JsonObject
import net.dungeonhub.provider.getAsJsonArrayOrNull
import net.dungeonhub.provider.getAsJsonObjectOrNull

//TODO map autopet
class MemberPetsData(
    val autoPet: JsonObject?,
    val pets: List<Pet>,
    val petCare: PetCareData?
)

fun JsonObject.toPetsData(): MemberPetsData {
    return MemberPetsData(
        getAsJsonObjectOrNull("autopet"),
        getAsJsonArrayOrNull("pets")?.asList()?.map { it.asJsonObject.toPet() } ?: emptyList(),
        getAsJsonObjectOrNull("pet_care")?.toPetCareData()
    )
}