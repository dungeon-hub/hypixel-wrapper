package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.SkyblockRarity

class KuudraTeethPlaque(raw: NBTCompound) : Memento(raw) {
    val rarity: SkyblockRarity
        get() = extraAttributes.getString("kuudraCavityRarity").let { SkyblockRarity.fromApiName(it) }
}