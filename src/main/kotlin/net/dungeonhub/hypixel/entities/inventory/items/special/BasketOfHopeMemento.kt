package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound

class BasketOfHopeMemento(raw: NBTCompound) : Memento(raw) {
    override val edition: Int?
        get() = extraAttributes.getInt("basket_edition", -1).takeIf { it != -1 }

    override val recipientName: String
        get() = extraAttributes.getString("basket_player_name")
}