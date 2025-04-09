package net.dungeonhub.hypixel.entities.inventory.items.id

import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem
import net.dungeonhub.hypixel.entities.inventory.items.special.HollowWand
import net.dungeonhub.hypixel.entities.inventory.items.special.StaffOfTheRisingSun
import net.dungeonhub.hypixel.entities.inventory.items.special.Wand
import net.dungeonhub.hypixel.entities.inventory.items.special.WizardWand

enum class WandItemId(override val apiName: String, override val itemClass: ((SkyblockItem) -> SkyblockItem)) :
    KnownSkyblockItemId {
    AspectOfTheLeechUncommon("ASPECT_OF_THE_LEECH_1", { Wand(it.raw) }),
    AspectOfTheLeechRare("ASPECT_OF_THE_LEECH_2", { Wand(it.raw) }),
    AspectOfTheLeechEpic("ASPECT_OF_THE_LEECH_3", { Wand(it.raw) }),
    BingoBlaster("BINGO_BLASTER", { Wand(it.raw) }),
    CelesteWand("CELESTE_WAND", { Wand(it.raw) }),
    FireVeilWand("FIRE_VEIL_WAND", { Wand(it.raw) }),
    GyrokineticWand("GYROKINETIC_WAND", { Wand(it.raw) }),
    HellstormWand("HELLSTORM_STAFF", { Wand(it.raw) }),
    HollowWand("HOLLOW_WAND", { HollowWand(it.raw) }),
    StaffOfTheRisingMoon("STAFF_OF_THE_RISING_MOON", { Wand(it.raw) }),
    StaffOfTheRisingSun("HOPE_OF_THE_RESISTANCE", { StaffOfTheRisingSun(it.raw) }),
    GeneralsStaffOfTheRisingSun("GENERALS_HOPE_OF_THE_RESISTANCE", { StaffOfTheRisingSun(it.raw) }),
    StaffOfTheVolcano("STAFF_OF_THE_VOLCANO", { Wand(it.raw) }),
    StarlightWand("STARLIGHT_WAND", { Wand(it.raw) }),
    TheAlchemistsStaff("ALCHEMISTS_STAFF", { Wand(it.raw) }),
    WandOfAtonement("WAND_OF_ATONEMENT", { Wand(it.raw) }),
    WandOfFarming("FARMING_WAND", { Wand(it.raw) }),
    WandOfHealing("WAND_OF_HEALING", { Wand(it.raw) }),
    WandOfMending("WAND_OF_MENDING", { Wand(it.raw) }),
    WandOfRestoration("WAND_OF_RESTORATION", { Wand(it.raw) }),
    WandOfStrength("WAND_OF_STRENGTH", { Wand(it.raw) }),
    WizardWand("WIZARD_WAND", { WizardWand(it.raw) });
}