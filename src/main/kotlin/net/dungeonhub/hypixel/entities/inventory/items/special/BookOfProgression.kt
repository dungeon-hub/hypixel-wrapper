package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.SkyblockRarity
import net.dungeonhub.hypixel.entities.inventory.items.Accessory

class BookOfProgression(raw: NBTCompound) : Accessory(raw) {
    val upgradedRarity: SkyblockRarity
        get() = SkyblockRarity.fromApiName(extraAttributes.getString("upgradedRarity"))
}