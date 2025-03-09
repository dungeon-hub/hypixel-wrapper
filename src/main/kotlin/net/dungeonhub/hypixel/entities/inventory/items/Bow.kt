package net.dungeonhub.hypixel.entities.inventory.items

import me.nullicorn.nedit.type.NBTCompound

//TODO check fields
open class Bow(raw: NBTCompound) : Weapon(raw) {
    val toxophiliteExperience: Double
        get() = extraAttributes.getDouble("toxophilite_combat_xp", 0.0)
}