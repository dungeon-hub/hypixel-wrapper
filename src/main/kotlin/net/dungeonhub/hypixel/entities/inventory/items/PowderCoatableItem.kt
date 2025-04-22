package net.dungeonhub.hypixel.entities.inventory.items

interface PowderCoatableItem : SkyblockItemFactory {
    val powderCoating: Boolean
        get() = extraAttributes.getInt("divan_powder_coating", 0) == 1
}