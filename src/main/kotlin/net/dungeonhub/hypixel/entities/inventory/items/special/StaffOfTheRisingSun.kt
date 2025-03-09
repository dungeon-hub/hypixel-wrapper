package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Gear

class StaffOfTheRisingSun(raw: NBTCompound) : Gear(raw) {
    val leaderVotes: Int
        get() = extraAttributes.getInt("leaderVotes", 0)

    val leaderPosition: Int
        get() = extraAttributes.getInt("leaderPosition", 0)
}