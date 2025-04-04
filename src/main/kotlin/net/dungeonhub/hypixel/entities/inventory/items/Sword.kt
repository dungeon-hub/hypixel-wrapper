package net.dungeonhub.hypixel.entities.inventory.items

import me.nullicorn.nedit.type.NBTCompound

// Also used for other melee weapons, so it's theoretically a MeleeWeapon
open class Sword(raw: NBTCompound) : Weapon(raw) {
    val championXp: Double?
        get() = extraAttributes.getDouble("champion_combat_xp", -1.0).takeIf { it != -1.0 }

    val woodSingularity: Boolean
        get() = extraAttributes.getInt("wood_singularity_count", 0) == 1
}