package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem
import net.dungeonhub.mojang.entity.toUUID
import java.util.*

class FruitBowl(raw: NBTCompound) : SkyblockItem(raw) {
    val namesFound: List<String>
        get() = extraAttributes.getList("names_found")?.map { it.toString() } ?: emptyList()

    val playersClicked: List<UUID>
        get() = extraAttributes.getList("fd15005d4e0443cdaf480ed7b6fc8d22")?.map { it.toString().toUUID() }
            ?: emptyList()
}