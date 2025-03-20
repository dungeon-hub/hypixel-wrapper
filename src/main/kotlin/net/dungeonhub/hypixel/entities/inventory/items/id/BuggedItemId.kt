package net.dungeonhub.hypixel.entities.inventory.items.id

import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

enum class BuggedItemId(override val apiName: String, override val itemClass: (SkyblockItem) -> SkyblockItem) :
    KnownSkyblockItemId {
    NullMap1("MAP:5"),
    NullMap2("MAP:19"),
    NullMap3("MAP:96"),
    NullStairs1("WOOD_STEP:11"),
    NullStairs2("WOOD_STEP:13"),
    NullStairs3("STEP:8"),
    NullStairs4("STEP:11"),
    NullStairs5("STEP:13"),
    NullSlab("STEP:2"),
    WaterSource("STATIONARY_WATER"),
    LavaSource("STATIONARY_LAVA"),
    LavaFlowing("LAVA"),
    NullObsidian("OBSIDIAN:8"),
    NullSteveSkull("SKULL_ITEM:3"),
    NullRod1("FISHING_ROD:16"),
    NullRod2("FISHING_ROD:50"),
    NullWood1("WOOD:7"),
    NullWood2("WOOD:9"),
    NullLog("LOG_2:3"),
    NullSand1("SAND:5"),
    NullSand2("SAND:9"),
    NullDirt("DIRT:9"),
    NullLeatherBoots1("LEATHER_BOOTS:30"),
    NullLeatherBoots2("LEATHER_BOOTS:40"),
    NullLeatherBoots3("LEATHER_BOOTS:42"),
    NullLeatherBoots4("LEATHER_BOOTS:48"),
    NullLeatherBoots5("LEATHER_BOOTS:58"),
    NullBow("BOW:96"),
    NullStone("STONE:9"),
    NullSkull("SKULL_ITEM:5"),
    EndermanSpawnEgg("MONSTER_EGG:58"),
    NullFlower1("YELLOW_FLOWER:4"),
    NullFlower2("RED_ROSE:15"),
    NullHay("HAY_BLOCK:3");

    constructor(apiName: String) : this(apiName, { it })
}