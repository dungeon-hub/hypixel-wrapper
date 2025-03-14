package net.dungeonhub.hypixel.entities.inventory.items.id

import net.dungeonhub.hypixel.entities.inventory.items.Armor
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

/**
 * These are item ids for items that are simply used in GUIs to display something.
 * An example for this is the Gemstone Item in the Collection Menu, or the Cancel Parkour Block you get into your Hotbar. They hold no function and are not obtainable, but they still theoretically exist as valid Skyblock Items.
 */
enum class DisplayItemId(override val apiName: String, override val itemClass: ((SkyblockItem) -> SkyblockItem)) :
    KnownSkyblockItemId {
    CancelParkour("CANCEL_PARKOUR_ITEM"),
    CancelRace("CANCEL_RACE_ITEM"),
    CatacombsPass0("MASTER_CATACOMBS_PASS_0"),
    CatacombsPass1("MASTER_CATACOMBS_PASS_1"),
    CatacombsPass10("MASTER_CATACOMBS_PASS_10"),
    CatacombsPass2("MASTER_CATACOMBS_PASS_2"),
    CatacombsPass3("MASTER_CATACOMBS_PASS_3"),
    CatacombsPass4("MASTER_CATACOMBS_PASS_4"),
    CatacombsPass5("MASTER_CATACOMBS_PASS_5"),
    CatacombsPass6("MASTER_CATACOMBS_PASS_6"),
    CatacombsPass7("MASTER_CATACOMBS_PASS_7"),
    CatacombsPass8("MASTER_CATACOMBS_PASS_8"),
    CatacombsPass9("MASTER_CATACOMBS_PASS_9"),
    DeadgehogHelmet("DEADGEHOG_HELMET", { Armor(it.raw) }),
    ExplosiveShotAbility("EXPLOSIVE_SHOT_ABILITY"),
    GemstoneCollection("GEMSTONE_COLLECTION"),
    KeyX("KEY_X"),
    LargePotionBag("LARGE_POTION_BAG"),
    MediumPotionBag("MEDIUM_POTION_BAG"),
    MushroomCollection("MUSHROOM_COLLECTION"),
    SmallPotionBag("SMALL_POTION_BAG"); //TODO test how this item has been found in testing data???

    constructor(apiName: String) : this(apiName, { it })
}