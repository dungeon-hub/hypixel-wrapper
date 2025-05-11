package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.ShinyItem
import net.dungeonhub.hypixel.entities.inventory.items.TeleportationSword
import net.dungeonhub.hypixel.entities.inventory.items.id.KnownNecronsScroll
import net.dungeonhub.hypixel.entities.inventory.items.id.NecronsScrollId

class WitherBlade(raw: NBTCompound) : TeleportationSword(raw), ShinyItem {
    val necronsScrolls: List<NecronsScrollId>
        get() = extraAttributes.getList("ability_scroll")?.map { KnownNecronsScroll.fromApiName(it.toString()) }
            ?: emptyList()
}