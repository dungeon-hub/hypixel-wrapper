package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

class LeaderboardItem(raw: NBTCompound) : SkyblockItem(raw) {
    val leaderboardScore: Int
        get() = extraAttributes.getInt("leaderboard_score", 0)

    val leaderboardPosition: Int
        get() = extraAttributes.getInt("leaderboard_position", 0)

    val leaderboardPlayer: String
        get() = extraAttributes.getString("leaderboard_player")

    /**
     * I have no idea what this field could mean other than in which SB year it's obtained, as that item isn't a New Years Cake
     */
    val yearCounter: Int?
        get() = extraAttributes.getInt("new_years_cake", -1).takeIf { it != -1 }

    val event: String?
        get() = extraAttributes.getString("event")
}