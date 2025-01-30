package net.dungeonhub.hypixel.entities.inventory.items

import me.nullicorn.nedit.type.NBTCompound

class Abiphone(raw: NBTCompound) : SkyblockItem(raw) {
    //TODO map to enum
    val abiphoneModel: String?
        get() = extraAttributes.getString("model")
}