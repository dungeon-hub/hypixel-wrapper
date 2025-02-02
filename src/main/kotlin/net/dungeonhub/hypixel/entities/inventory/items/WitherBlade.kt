package net.dungeonhub.hypixel.entities.inventory.items

import me.nullicorn.nedit.type.NBTCompound

class WitherBlade(raw: NBTCompound) : TeleportationSword(raw), ShinyItem {
    val abilityScrolls: List<String>
        get() = extraAttributes.getList("ability_scroll")?.map { it.toString() } ?: emptyList()
}