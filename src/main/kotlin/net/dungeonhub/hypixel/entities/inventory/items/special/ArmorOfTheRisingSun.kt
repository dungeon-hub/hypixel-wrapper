package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Armor

class ArmorOfTheRisingSun(raw: NBTCompound) : Armor(raw) {
    val leaderVotes: Int?
        get() = extraAttributes.getInt("leaderVotes", -1).takeIf { it != -1 }

    val leaderPosition: Int?
        get() = extraAttributes.getInt("leaderPosition", -1).takeIf { it != -1 }
}