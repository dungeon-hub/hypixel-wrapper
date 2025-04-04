package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.ShinyItem
import net.dungeonhub.hypixel.entities.inventory.items.TeleportationSword

class WitherBlade(raw: NBTCompound) : TeleportationSword(raw), ShinyItem {
    val abilityScrolls: List<String>
        get() = extraAttributes.getList("ability_scroll")?.map { it.toString() } ?: emptyList()
}