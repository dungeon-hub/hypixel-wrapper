package net.dungeonhub.hypixel.entities.inventory.items.id

import net.dungeonhub.hypixel.entities.inventory.items.Gear
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem
import net.dungeonhub.hypixel.entities.inventory.items.special.StaffOfTheRisingSun

enum class WandItemId(override val apiName: String, override val itemClass: ((SkyblockItem) -> SkyblockItem)) :
    KnownSkyblockItemId {
    AspectOfTheLeechUncommon("ASPECT_OF_THE_LEECH_1", { Gear(it.raw) }),
    AspectOfTheLeechRare("ASPECT_OF_THE_LEECH_2", { Gear(it.raw) }),
    AspectOfTheLeechEpic("ASPECT_OF_THE_LEECH_3", { Gear(it.raw) }),
    BingoBlaster("BINGO_BLASTER", { Gear(it.raw) }),
    CelesteWand("CELESTE_WAND", { Gear(it.raw) }),
    FireVeilWand("FIRE_VEIL_WAND", { Gear(it.raw) }),
    GyrokineticWand("GYROKINETIC_WAND", { Gear(it.raw) }),
    HellstormWand("HELLSTORM_STAFF", { Gear(it.raw) }),
    HollowWand("HOLLOW_WAND", { Gear(it.raw) }),
    StaffOfTheRisingMoon("STAFF_OF_THE_RISING_MOON"),
    StaffOfTheRisingSun("HOPE_OF_THE_RESISTANCE", { StaffOfTheRisingSun(it.raw) }),
    GeneralsStaffOfTheRisingSun("GENERALS_HOPE_OF_THE_RESISTANCE", { Gear(it.raw) }),
    StaffOfTheVolcano("STAFF_OF_THE_VOLCANO", { Gear(it.raw) }),
    StarlightWand("STARLIGHT_WAND", { Gear(it.raw) }),
    TheAlchemistsStaff("ALCHEMISTS_STAFF", { Gear(it.raw) }),
    WandOfAtonement("WAND_OF_ATONEMENT", { Gear(it.raw) }),
    WandOfFarming("FARMING_WAND"),
    WandOfHealing("WAND_OF_HEALING", { Gear(it.raw) }),
    WandOfMending("WAND_OF_MENDING", { Gear(it.raw) }),
    WandOfRestoration("WAND_OF_RESTORATION", { Gear(it.raw) }),
    WandOfStrength("WAND_OF_STRENGTH", { Gear(it.raw) }),
    WizardWand("WIZARD_WAND", { Gear(it.raw) });

    constructor(apiName: String) : this(apiName, { it })
}