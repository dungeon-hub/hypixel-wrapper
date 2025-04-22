package net.dungeonhub.hypixel.entities.inventory.items

import me.nullicorn.nedit.type.NBTCompound

open class Armor(raw: NBTCompound) : Gear(raw), ItemWithHotPotatoBooks, SkinAppliable, ItemFromBoss, DungeonItem,
    GearFromOphelia {
    val artOfPeace: Boolean
        get() = extraAttributes.getInt("artOfPeaceApplied", 0) == 1

    val color: String //TODO can this be parsed to an actual java color? format is: 0:0:0
        get() = extraAttributes.getString("color")

    val hecatombRuns: Int
        get() = extraAttributes.getInt("hecatomb_s_runs", 0)

    val historicDungeonScore: Int
        get() = extraAttributes.getInt("historic_dungeon_score", 0)
}