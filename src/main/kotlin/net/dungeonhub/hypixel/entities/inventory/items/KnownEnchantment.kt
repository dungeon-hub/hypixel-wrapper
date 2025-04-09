package net.dungeonhub.hypixel.entities.inventory.items

enum class KnownEnchantment(
    override val apiName: String,
    val ultimate: Boolean,
    vararg targets: EnchantmentTarget
) : Enchantment {
    Angler("angler", false, EnchantmentTarget.FishingRods),
    AquaAffinity("aqua_affinity", false, EnchantmentTarget.Armor),
    BaneOfArthropods("bane_of_arthropods", false, EnchantmentTarget.Swords),
    BigBrain("big_brain", false, EnchantmentTarget.Armor),
    BlastProtection("blast_protection", false, EnchantmentTarget.Armor),
    Blessing("blessing", false, EnchantmentTarget.FishingRods),
    Caster("caster", false, EnchantmentTarget.FishingRods),
    Cayenne("cayenne", false, EnchantmentTarget.Equipment),
    Champion("champion", false, EnchantmentTarget.Swords),
    Chance("chance", false, EnchantmentTarget.Bows),
    Charm("charm", false, EnchantmentTarget.FishingRods),
    Cleave("cleave", false, EnchantmentTarget.Swords),
    Compact("compact", false, EnchantmentTarget.Tools),
    Corruption("corruption", false, EnchantmentTarget.FishingRods),
    CounterStrike("counter_strike", false, EnchantmentTarget.Armor),
    Critical("critical", false, EnchantmentTarget.Swords),
    Cubism("cubism", false, EnchantmentTarget.Swords, EnchantmentTarget.Bows),
    Cultivating("cultivating", false, EnchantmentTarget.Tools),
    Dedication("dedication", false, EnchantmentTarget.Tools),
    Delicate("delicate", false, EnchantmentTarget.Tools),
    DepthStrider("depth_strider", false, EnchantmentTarget.Armor),
    DivineGift("divine_gift", false, EnchantmentTarget.Swords, EnchantmentTarget.Bows),
    DragonHunter("dragon_hunter", false, EnchantmentTarget.Swords, EnchantmentTarget.Bows),
    DragonTracer("aiming", false, EnchantmentTarget.Bows),
    Efficiency("efficiency", false, EnchantmentTarget.Tools),
    EnderSlayer("ender_slayer", false, EnchantmentTarget.Swords),
    Execute("execute", false, EnchantmentTarget.Swords),
    Experience("experience", false, EnchantmentTarget.Swords, EnchantmentTarget.Tools),
    Expertise("expertise", false, EnchantmentTarget.FishingRods),
    FeatherFalling("feather_falling", false, EnchantmentTarget.Armor),
    FerociousMana("ferocious_mana", false, EnchantmentTarget.Armor),
    FireAspect("fire_aspect", false, EnchantmentTarget.Swords),
    FireProtection("fire_protection", false, EnchantmentTarget.Armor),
    FirstStrike("first_strike", false, EnchantmentTarget.Swords),
    Flame("flame", false, EnchantmentTarget.Bows),
    Fortune("fortune", false, EnchantmentTarget.Tools),
    Frail("frail", false, EnchantmentTarget.FishingRods),
    FrostWalker("frost_walker", false, EnchantmentTarget.Armor),
    GiantKiller("giant_killer", false, EnchantmentTarget.Swords),
    GreatSpook("great_spook", false, EnchantmentTarget.Armor),
    GreenThumb("green_thumb", false, EnchantmentTarget.Equipment),
    Growth("growth", false, EnchantmentTarget.Armor),
    HardenedMana("hardened_mana", false, EnchantmentTarget.Armor),
    Harvesting("harvesting", false, EnchantmentTarget.Tools),
    Hecatomb("hecatomb", false, EnchantmentTarget.Armor),
    IceCold("ice_cold", false, EnchantmentTarget.Armor),
    Impaling("impaling", false, EnchantmentTarget.Swords, EnchantmentTarget.Bows, EnchantmentTarget.FishingRods),
    InfiniteQuiver("infinite_quiver", false, EnchantmentTarget.Bows),
    Knockback("knockback", false, EnchantmentTarget.Swords),
    Lapidary("lapidary", false, EnchantmentTarget.Tools),
    Lethality("lethality", false, EnchantmentTarget.Swords),
    LifeSteal("life_steal", false, EnchantmentTarget.Swords),
    Looting("looting", false, EnchantmentTarget.Swords, EnchantmentTarget.FishingRods),
    Luck("luck", false, EnchantmentTarget.Swords),
    LuckOfTheSea("luck_of_the_sea", false, EnchantmentTarget.FishingRods),
    Lure("lure", false, EnchantmentTarget.FishingRods),
    Magnet("magnet", false, EnchantmentTarget.FishingRods),
    ManaSteal("mana_steal", false, EnchantmentTarget.Swords),
    ManaVampire("mana_vampire", false, EnchantmentTarget.Armor),
    Overload("overload", false, EnchantmentTarget.Bows),
    Paleontologist("paleontologist", false, EnchantmentTarget.Tools),
    Pesterminator("pesterminator", false, EnchantmentTarget.Armor),
    Piercing("piercing", false, EnchantmentTarget.Bows),
    Piscary("piscary", false, EnchantmentTarget.FishingRods),
    Power("power", false, EnchantmentTarget.Bows),
    Prismatic("pristine", false, EnchantmentTarget.Tools),
    ProjectileProtection("projectile_protection", false, EnchantmentTarget.Armor),
    Prosecute("prosecute", false, EnchantmentTarget.Swords),
    Prosperity("prosperity", false, EnchantmentTarget.Equipment),
    Protection("protection", false, EnchantmentTarget.Armor),
    Punch("punch", false, EnchantmentTarget.Bows),
    Quantum("quantum", false, EnchantmentTarget.Equipment),
    QuickBite("quick_bite", false, EnchantmentTarget.FishingRods),
    Rainbow("rainbow", false, EnchantmentTarget.Tools),
    Reflection("reflection", false, EnchantmentTarget.Armor),
    Rejuvenate("rejuvenate", false, EnchantmentTarget.Armor),
    Replenish("replenish", false, EnchantmentTarget.Tools),
    Respiration("respiration", false, EnchantmentTarget.Armor),
    Respite("respite", false, EnchantmentTarget.Armor),
    Scavenger("scavenger", false, EnchantmentTarget.Swords),
    Sharpness("sharpness", false, EnchantmentTarget.Swords),
    SilkTouch("silk_touch", false, EnchantmentTarget.Tools),
    SmallBrain("small_brain", false, EnchantmentTarget.Armor),
    SmartyPants("smarty_pants", false, EnchantmentTarget.Armor),
    SmeltingTouch("smelting_touch", false, EnchantmentTarget.Tools),
    Smite("smite", false, EnchantmentTarget.Swords),
    Smoldering("smoldering", false, EnchantmentTarget.Swords, EnchantmentTarget.Bows),
    Snipe("snipe", false, EnchantmentTarget.Bows),
    SpikedHook("spiked_hook", false, EnchantmentTarget.FishingRods),
    StrongMana("strong_mana", false, EnchantmentTarget.Armor),
    SugarRush("sugar_rush", false, EnchantmentTarget.Armor),
    Sunder("sunder", false, EnchantmentTarget.Tools),
    Syphon("syphon", false, EnchantmentTarget.Swords),
    Tabasco(
        "tabasco",
        false,
        EnchantmentTarget.Swords,
        EnchantmentTarget.Bows,
        EnchantmentTarget.FishingRods,
        EnchantmentTarget.Tools
    ),
    Thorns("thorns", false, EnchantmentTarget.Armor),
    Thunderbolt("thunderbolt", false, EnchantmentTarget.Swords),
    Thunderlord("thunderlord", false, EnchantmentTarget.Swords),
    Tidal("tidal", false, EnchantmentTarget.Armor),
    TitanKiller("titan_killer", false, EnchantmentTarget.Swords),
    Toxophilite("toxophilite", false, EnchantmentTarget.Bows),
    Transylvanian("transylvanian", false, EnchantmentTarget.Armor),
    TripleStrike("triple_strike", false, EnchantmentTarget.Swords),
    TrueProtection("true_protection", false, EnchantmentTarget.Armor),
    TurboCacti("turbo_cacti", false, EnchantmentTarget.Tools),
    TurboCane("turbo_cane", false, EnchantmentTarget.Tools),
    TurboCarrot("turbo_carrot", false, EnchantmentTarget.Tools),
    TurboCocoa("turbo_coco", false, EnchantmentTarget.Tools),
    TurboMelon("turbo_melon", false, EnchantmentTarget.Tools),
    TurboMushrooms("turbo_mushrooms", false, EnchantmentTarget.Tools),
    TurboPotato("turbo_potato", false, EnchantmentTarget.Tools),
    TurboPumpkin("turbo_pumpkin", false, EnchantmentTarget.Tools),
    TurboWarts("turbo_warts", false, EnchantmentTarget.Tools),
    TurboWheat("turbo_wheat", false, EnchantmentTarget.Tools),
    Vampirism("vampirism", false, EnchantmentTarget.Swords),
    Venomous("venomous", false, EnchantmentTarget.Swords),
    Vicious("vicious", false, EnchantmentTarget.Swords, EnchantmentTarget.Bows),

    Bank("ultimate_bank", true, EnchantmentTarget.Armor),
    BobbinTime("ultimate_bobbin_time", true, EnchantmentTarget.Armor),
    Chimera("ultimate_chimera", true, EnchantmentTarget.Swords),
    Combo("ultimate_combo", true, EnchantmentTarget.Swords),
    Duplex("ultimate_reiterate", true, EnchantmentTarget.Bows),
    FatalTempo(
        "ultimate_fatal_tempo",
        true,
        EnchantmentTarget.Swords,
        EnchantmentTarget.Bows,
        EnchantmentTarget.FishingRods,
        EnchantmentTarget.Tools
    ),
    Flash("ultimate_flash", true, EnchantmentTarget.FishingRods),
    Flowstate("ultimate_flowstate", true, EnchantmentTarget.Tools),
    HabaneroTactics("ultimate_habanero_tactics", true, EnchantmentTarget.Armor),
    Inferno(
        "ultimate_inferno",
        true,
        EnchantmentTarget.Swords,
        EnchantmentTarget.Bows,
        EnchantmentTarget.FishingRods,
        EnchantmentTarget.Tools
    ),
    LastStand("ultimate_last_stand", true, EnchantmentTarget.Armor),
    Legion("ultimate_legion", true, EnchantmentTarget.Armor),
    NoPainNoGain("ultimate_no_pain_no_gain", true, EnchantmentTarget.Armor),
    OneForAll("ultimate_one_for_all", true, EnchantmentTarget.Swords),
    Refrigerate("ultimate_refrigerate", true, EnchantmentTarget.Armor),
    Rend("ultimate_rend", true, EnchantmentTarget.Bows),
    SoulEater("ultimate_soul_eater", true, EnchantmentTarget.Swords, EnchantmentTarget.Bows),
    Swarm("ultimate_swarm", true, EnchantmentTarget.Swords, EnchantmentTarget.Bows),
    TheOne("ultimate_the_one", true, EnchantmentTarget.Equipment),
    UltimateJerry("ultimate_jerry", true, EnchantmentTarget.Swords),
    UltimateWise(
        "ultimate_wise",
        true,
        EnchantmentTarget.Swords,
        EnchantmentTarget.Bows,
        EnchantmentTarget.Wands,
        EnchantmentTarget.FishingRods,
        EnchantmentTarget.Tools
    ),
    Wisdom("ultimate_wisdom", true, EnchantmentTarget.Armor);

    enum class DeprecatedEnchantment(
        override val apiName: String,
        val ultimate: Boolean,
        vararg targets: EnchantmentTarget
    ) : Enchantment {
        Telekinesis(
            "telekinesis", false, EnchantmentTarget.Swords, EnchantmentTarget.Bows, EnchantmentTarget.Tools,
            EnchantmentTarget.FishingRods, EnchantmentTarget.Wands
        ),
        TurboCactus(
            "turbo_cactus",
            false,
            EnchantmentTarget.Tools
        ); //TODO check if thats actually just an old name or if the wiki is just incorrect (again)
    }

    enum class EnchantmentTarget {
        Swords,
        Bows,
        Armor,
        Tools,
        FishingRods,
        Wands,
        Equipment;
    }

    class UnknownEnchantment(override val apiName: String) : Enchantment

    companion object {
        fun fromApiName(apiName: String): Enchantment {
            return KnownEnchantment.entries.firstOrNull {
                it.apiName == apiName
            } ?: DeprecatedEnchantment.entries.firstOrNull {
                it.apiName == apiName
            } ?: UnknownEnchantment(apiName)
        }
    }
}