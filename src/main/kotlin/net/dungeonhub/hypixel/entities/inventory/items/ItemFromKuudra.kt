package net.dungeonhub.hypixel.entities.inventory.items

interface ItemFromKuudra : SkyblockItemFactory {
    val bossTierOrigin: Int?
        get() = extraAttributes.getInt("boss_tier", -1).takeIf { it != -1 }

    /* TODO add this to the following items:
    BURNING_KUUDRA_CORE
    ENRAGER
    FIERY_KUUDRA_CORE
    HOLLOW_WAND
    INFERNAL_KUUDRA_CORE
    KUUDRA_MANDIBLE
    MANDRAA
    RUNIC_STAFF
    */
}