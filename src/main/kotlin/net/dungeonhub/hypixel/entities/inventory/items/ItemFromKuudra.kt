package net.dungeonhub.hypixel.entities.inventory.items

interface ItemFromKuudra : SkyblockItemFactory {
    val bossTierOrigin: Int?
        get() = extraAttributes.getInt("boss_tier", -1).takeIf { it != -1 }
}