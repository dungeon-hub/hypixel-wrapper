package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

class SprayCan(raw: NBTCompound) : SkyblockItem(raw) {
    val spray: String?
        get() = extraAttributes.getString("spray")

    val orange: Int?
        get() = extraAttributes.getInt("orange", -1).takeIf { it != -1 }

    val red: Int?
        get() = extraAttributes.getInt("red", -1).takeIf { it != -1 }

    val magenta: Int?
        get() = extraAttributes.getInt("magenta", -1).takeIf { it != -1 }

    val pink: Int?
        get() = extraAttributes.getInt("pink", -1).takeIf { it != -1 }

    val white: Int?
        get() = extraAttributes.getInt("white", -1).takeIf { it != -1 }

    val yellow: Int?
        get() = extraAttributes.getInt("yellow", -1).takeIf { it != -1 }

    val lightBlue: Int?
        get() = extraAttributes.getInt("light_blue", -1).takeIf { it != -1 }

    val silver: Int?
        get() = extraAttributes.getInt("silver", -1).takeIf { it != -1 }

    val green: Int?
        get() = extraAttributes.getInt("green", -1).takeIf { it != -1 }

    val lime: Int?
        get() = extraAttributes.getInt("lime", -1).takeIf { it != -1 }

    val black: Int?
        get() = extraAttributes.getInt("black", -1).takeIf { it != -1 }

    val brown: Int?
        get() = extraAttributes.getInt("brown", -1).takeIf { it != -1 }

    val blue: Int?
        get() = extraAttributes.getInt("blue", -1).takeIf { it != -1 }

    val purple: Int?
        get() = extraAttributes.getInt("purple", -1).takeIf { it != -1 }

    val cyan: Int?
        get() = extraAttributes.getInt("cyan", -1).takeIf { it != -1 }

    val gray: Int?
        get() = extraAttributes.getInt("gray", -1).takeIf { it != -1 }

    val orientation: String?
        get() = extraAttributes.getString("orientation")
}