package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

class GodPotion(raw: NBTCompound) : SkyblockItem(raw) {
    val mixins: List<String> //TODO map to mixin enum?
        get() = extraAttributes.getList("mixins").map { it.toString() }
}