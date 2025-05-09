package net.dungeonhub.hypixel.entities.inventory.items

import com.google.gson.JsonObject
import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.skyblock.pet.Pet
import net.dungeonhub.hypixel.entities.skyblock.pet.toPet
import net.dungeonhub.provider.GsonProvider

class PetAsItem(raw: NBTCompound) : SkyblockItem(raw), ItemFromBoss, EditionItem, ItemWithGreatSpookYear {
    val petInfo: Pet?
        get() = extraAttributes.getString("petInfo")?.let { GsonProvider.gson.fromJson(it, JsonObject::class.java) }
            ?.toPet()
}