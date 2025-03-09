package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.SkyblockRarity
import net.dungeonhub.hypixel.entities.inventory.items.Accessory

class PandorasBox(raw: NBTCompound) : Accessory(raw) {
    val pandoraRarity: SkyblockRarity
        get() = SkyblockRarity.fromApiName(extraAttributes.getString("pandora-rarity"))
}