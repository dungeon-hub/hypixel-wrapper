package net.dungeonhub.hypixel.entities.inventory

import me.nullicorn.nedit.type.NBTCompound
import java.util.*

class ItemGemsData(
    val unlockedSlots: List<String>,
    val appliedGemstones: Map<String, AppliedGemstone>,
    val gemstoneType: Map<String, String>
)

//TODO better support gemstones in universal slots, with format:
//COMBAT_0=FINE
//COMBAT_0_gem=JASPER
fun NBTCompound.toGemsData(): ItemGemsData {
    return ItemGemsData(
        getList("unlocked_slots")?.map { it.toString() } ?: emptyList(),
        entries.filter { it.key != "unlocked_slots" && !it.key.endsWith("_gem") }.associate {
            if(it.value is NBTCompound) {
                val details = it.value as NBTCompound

                it.key to AdvancedAppliedGemstone(GemstoneQuality.fromApiName(details.getString("quality"))!!, UUID.fromString(details.getString("uuid")))
            } else {
                it.key to SimpleAppliedGemstone(GemstoneQuality.fromApiName(it.value.toString())!!)
            }
        },
        entries.filter { it.key != "unlocked_slots" && it.key.endsWith("_gem") }.associate {
            it.key to it.value.toString()
        }
    )
}