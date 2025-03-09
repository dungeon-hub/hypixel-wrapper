package net.dungeonhub.hypixel.entities.inventory.items.id

import net.dungeonhub.hypixel.entities.inventory.items.*

enum class AdminItemId(override val apiName: String, override val itemClass: ((SkyblockItem) -> SkyblockItem)) :
    KnownSkyblockItemId {
    AdminLavaRod("ADMIN_ROD"),
    AdminsWarmSweater("ADMINS_WARM_SWEATER", { Armor(it.raw) }),
    Anubis("ANUBIS", { Armor(it.raw) }),
    ArenjeyGod("MEGA_LUCK", { Armor(it.raw) }),
    Boss("BOSS", { Armor(it.raw) }),
    BowOfTheUniverse("UNIVERSE_BOW", { Bow(it.raw) }),
    EnchantedClockAdmin("ENCHANTED_CLOCK"),
    FerocitySword150("FEROCITY_SWORD_150", { Weapon(it.raw) }),
    FerocitySword50("FEROCITY_SWORD_50", { Weapon(it.raw) }),
    GiantPileOfCash("PILE_OF_CASH"),
    Kindred("KINDRED", { Armor(it.raw) }),
    Raygun("RAYGUN", { Bow(it.raw) }),
    SwordOfTheMultiverse("SWORD_OF_THE_MULTIVERSE", { Weapon(it.raw) }),
    SwordOfTheStars("STAR_SWORD", { Weapon(it.raw) }),
    SwordOfTheStars3000("STAR_SWORD_3000", { Weapon(it.raw) }),
    SwordOfTheStars9000("STAR_SWORD_9000", { Weapon(it.raw) }),
    SwordOfTheUniverse("NOVA_SWORD", { Weapon(it.raw) }),
    SwordOfLiveReloading("SWORD_OF_LIVE_RELOADING", { Weapon(it.raw) }),
    HelmetOfLiveReloading("HELMET_OF_LIVE_RELOADING", { Armor(it.raw) }),
    TheFast("SPEED_RACER", { Armor(it.raw) }),
    Voodoo("VOODOO", { Armor(it.raw) }),
    Zoom("ZOOM_PICKAXE", { Gear(it.raw) });

    constructor(apiName: String) : this(apiName, { it })
}