package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound

class SecretBingoCard(raw: NBTCompound) : BingoCard(raw) {
    val completion: Int?
        get() = extraAttributes.getInt("completion", -1).takeIf { it != -1 }
}