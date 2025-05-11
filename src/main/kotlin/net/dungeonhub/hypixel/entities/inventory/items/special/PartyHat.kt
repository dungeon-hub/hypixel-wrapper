package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Accessory
import net.dungeonhub.hypixel.entities.inventory.items.id.AccessoryItemId

class PartyHat(raw: NBTCompound) : Accessory(raw) {
    override val uniqueName: String
        get() {
            return if (id == AccessoryItemId.SlothHatOfCelebration && emoji != null) {
                "${id.apiName}_${emoji?.uppercase()}"
            } else if (listOf(
                    AccessoryItemId.CrabHatOfCelebration,
                    AccessoryItemId.CrabHatOfCelebration2022Edition,
                    AccessoryItemId.FifthAnniversaryBalloonHat
                ).contains(id) && color != null
            ) {
                "${id.apiName}_${color?.uppercase()}"
            } else {
                super.uniqueName
            }
        }

    val year: Int
        get() = extraAttributes.getInt("party_hat_year", 0)

    val color: String?
        get() = extraAttributes.getString("party_hat_color")

    val emoji: String?
        get() = extraAttributes.getString("party_hat_emoji")
}