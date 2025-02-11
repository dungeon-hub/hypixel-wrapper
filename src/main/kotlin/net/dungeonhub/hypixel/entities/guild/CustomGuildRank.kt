package net.dungeonhub.hypixel.entities.guild

import java.time.Instant

class CustomGuildRank(
    override val name: String,
    override val tag: String?,
    override val default: Boolean,
    override val created: Instant,
    override var priority: Int
) : GuildRank {
    override val owner: Boolean = false
}