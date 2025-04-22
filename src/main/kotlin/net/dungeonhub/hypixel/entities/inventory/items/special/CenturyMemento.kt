package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound

class CenturyMemento(raw: NBTCompound) : Memento(raw) {
    val tickets: Int
        get() = extraAttributes.getInt("tickets", 0)

    val winningTeam: String
        get() = extraAttributes.getString("winning_team")

    val recipientTeam: String
        get() = extraAttributes.getString("recipient_team")

    val centuryYear: Int?
        get() = extraAttributes.getInt("century_year", -1).takeIf { it != -1 }
}