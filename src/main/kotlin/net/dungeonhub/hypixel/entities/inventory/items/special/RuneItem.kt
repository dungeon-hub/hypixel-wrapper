package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.ItemWithRune
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

class RuneItem(raw: NBTCompound) : SkyblockItem(raw), ItemWithRune {
    override val uniqueName: String
        get() {
            return if (runes.size == 1) {
                val rune = runes.toList()[0]
                return toRuneUniqueName(rune.first, rune.second)
            } else {
                super<SkyblockItem>.uniqueName
            }
        }

    companion object {
        fun toRuneUniqueName(type: String, level: Int): String {
            return "RUNE_${type}_${level}".uppercase()
        }
    }
}