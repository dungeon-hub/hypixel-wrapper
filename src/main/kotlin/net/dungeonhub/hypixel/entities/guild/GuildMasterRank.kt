package net.dungeonhub.hypixel.entities.guild

import java.time.Instant

class GuildMasterRank(
    override val tag: String?,
    override val default: Boolean,
    override val created: Instant,
    override var priority: Int
) : GuildRank {
    override val name: String = GUILD_MASTER_NAME
    override val owner: Boolean = true
    val type = "gm"

    companion object {
        const val GUILD_MASTER_NAME = "Guild Master"
    }
}