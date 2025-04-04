package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.mojang.entity.toUUID
import java.util.*

class BingosSecretMemento(raw: NBTCompound) : Memento(raw) {
    val goalName: String
        get() = extraAttributes.getString("goal_name")

    val bingoEvent: Int
        get() = extraAttributes.getInt("bingo_event", -1).takeIf { it != -1 }!!

    override val recipientName: String?
        get() = extraAttributes.getString("player_name")

    override val recipientUuid: UUID?
        get() = extraAttributes.getString("player_uuid")?.toUUID()
}