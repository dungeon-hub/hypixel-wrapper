package net.dungeonhub.hypixel.entities.inventory.items.id

import net.dungeonhub.hypixel.entities.inventory.items.Gear
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem
import net.dungeonhub.hypixel.entities.inventory.items.special.Pickonimbus

enum class ToolItemId(override val apiName: String, override val itemClass: ((SkyblockItem) -> SkyblockItem)) :
    KnownSkyblockItemId {
    //TODO - https://wiki.hypixel.net/Category:Tool

    RodOfTheSea("ROD_OF_THE_SEA", { Gear(it.raw) }),
    InfernoRod("INFERNO_ROD", { Gear(it.raw) }),
    Pickonimbus("PICKONIMBUS", { Pickonimbus(it.raw) }),
    Treecapitator("TREECAPITATOR_AXE", { Gear(it.raw) }),
    Stonk("STONK_PICKAXE", { Gear(it.raw) }),
    RodOfLegends("LEGEND_ROD", { Gear(it.raw) }),
    CocoChopper("COCO_CHOPPER"),
    FungiCutter("FUNGI_CUTTER");

    constructor(apiName: String) : this(apiName, { it })
}