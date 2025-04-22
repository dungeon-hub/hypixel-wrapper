package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.GearFromOphelia
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem
import net.dungeonhub.hypixel.entities.inventory.items.id.KnownSkyblockItemId
import net.dungeonhub.hypixel.entities.inventory.items.id.SkyblockItemId

class Potion(raw: NBTCompound) : SkyblockItem(raw), GearFromOphelia {
    val potionLevel: Int
        get() = extraAttributes.getInt("potion_level", 0)

    val potion: String?
        get() = extraAttributes.getString("potion")

    //TODO map to enum?
    val potionType: String?
        get() = extraAttributes.getString("potion_type")

    val splash: Boolean
        get() = extraAttributes.getInt("splash", 0) > 0

    //TODO map to object
    val effects: NBTCompound?
        get() = extraAttributes.getCompound("effects")

    val potionName: String?
        get() = extraAttributes.getString("potion_name")

    val dungeonPotion: Boolean
        get() = extraAttributes.getByte("dungeon_potion", 0) == 1.toByte()

    val enhanced: Boolean?
        get() = extraAttributes.getInt("enhanced", -1).takeIf { it != -1 }?.equals(1)

    val extended: Boolean?
        get() = extraAttributes.getInt("extended", -1).takeIf { it != -1 }?.equals(1)

    val lastIngredient: SkyblockItemId?
        get() = extraAttributes.getString("last_potion_ingredient")
            ?.let { KnownSkyblockItemId.fromApiName(it) }

    val shouldGiveAlchemyExperience: Boolean
        get() = extraAttributes.getByte("should_give_alchemy_exp", 0) == 1.toByte()
}