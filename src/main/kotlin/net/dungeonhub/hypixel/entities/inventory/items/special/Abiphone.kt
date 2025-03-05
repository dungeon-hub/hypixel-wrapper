package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.ItemWithAbility
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

class Abiphone(raw: NBTCompound) : SkyblockItem(raw), ItemWithAbility {
    //TODO map to enum
    //TODO check if this actually exists or was simply confused with the abicase
    val abiphoneModel: String?
        get() = extraAttributes.getString("model")
}