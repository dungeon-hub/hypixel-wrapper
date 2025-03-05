package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Accessory

class PartyHat(raw: NBTCompound) : Accessory(raw) {
    val year: Int
        get() = extraAttributes.getInt("party_hat_year", 0)

    val color: String?
        get() = extraAttributes.getString("party_hat_color")

    val emoji: String?
        get() = extraAttributes.getString("party_hat_emoji")
}