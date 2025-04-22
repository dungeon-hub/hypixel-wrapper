package net.dungeonhub.hypixel.entities.inventory.items

interface PromisingTool : SkyblockItemFactory {
    val blocksBroken: Int
        get() = extraAttributes.getInt("blocksBroken", 0)
}