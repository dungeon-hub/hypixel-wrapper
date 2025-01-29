package net.dungeonhub.hypixel.entities.inventory.items

//TODO complete mapping
//TODO should different types of armor/tools be mapped -> when enchantment is only for helmets/pickaxes
enum class KnownEnchantment(
    override val apiName: String,
    val ultimate: Boolean,
    vararg targets: EnchantmentTarget
) : Enchantment {
    BaneOfArthropods("bane_of_arthropods", false, EnchantmentTarget.Swords),
    Champion("champion", false, EnchantmentTarget.Swords),
    Chance("chance", false, EnchantmentTarget.Bows),
    Cleave("cleave", false, EnchantmentTarget.Swords),
    Critical("critical", false, EnchantmentTarget.Swords),
    Cubism("cubism", false, EnchantmentTarget.Swords, EnchantmentTarget.Bows),
    DivineGift("divine_gift", false, EnchantmentTarget.Swords, EnchantmentTarget.Bows),
    DragonHunter("dragon_hunter", false, EnchantmentTarget.Swords, EnchantmentTarget.Bows),
    DragonTracer("aiming", false, EnchantmentTarget.Bows),
    EnderSlayer("ender_slayer", false, EnchantmentTarget.Swords),
    Execute("execute", false, EnchantmentTarget.Swords),
    Experience("experience", false, EnchantmentTarget.Swords, EnchantmentTarget.Tools, EnchantmentTarget.FishingRods),
    FireAspect("fire_aspect", false, EnchantmentTarget.Swords),
    FirstStrike("first_strike", false, EnchantmentTarget.Swords),
    Flame("flame", false, EnchantmentTarget.Bows),
    GiantKiller("giant_killer", false, EnchantmentTarget.Swords),
    Impaling("impaling", false, EnchantmentTarget.Swords, EnchantmentTarget.Bows),
    InfiniteQuiver("infinite_quiver", false, EnchantmentTarget.Bows),
    Knockback("knockback", false, EnchantmentTarget.Swords),
    Lethality("lethality", false, EnchantmentTarget.Swords),
    LifeSteal("life_steal", false, EnchantmentTarget.Swords),
    Looting("looting", false, EnchantmentTarget.Swords, EnchantmentTarget.FishingRods),
    Luck("luck", false, EnchantmentTarget.Swords),
    ManaSteal("mana_steal", false, EnchantmentTarget.Swords),
    Overload("overload", false, EnchantmentTarget.Bows),
    Piercing("piercing", false, EnchantmentTarget.Bows),
    Power("power", false, EnchantmentTarget.Bows),
    Prosecute("prosecute", false, EnchantmentTarget.Swords),
    Punch("punch", false, EnchantmentTarget.Bows),
    Scavenger("scavenger", false, EnchantmentTarget.Swords),
    Sharpness("sharpness", false, EnchantmentTarget.Swords),
    Smite("smite", false, EnchantmentTarget.Swords),
    Smoldering("smoldering", false, EnchantmentTarget.Swords, EnchantmentTarget.Bows),
    Snipe("snipe", false, EnchantmentTarget.Bows),
    Syphon("syphon", false, EnchantmentTarget.Swords),
    Tabasco("tabasco", false, EnchantmentTarget.Swords, EnchantmentTarget.Bows),
    Thunderbolt("thunderbolt", false, EnchantmentTarget.Swords),
    Thunderlord("thunderlord", false, EnchantmentTarget.Swords),
    TitanKiller("titan_killer", false, EnchantmentTarget.Swords),
    Toxophilite("toxophilite", false, EnchantmentTarget.Bows),
    TripleStrike("triple_strike", false, EnchantmentTarget.Swords),
    Vampirism("vampirism", false, EnchantmentTarget.Swords),
    Venomous("venomous", false, EnchantmentTarget.Swords),
    Vicious("vicious", false, EnchantmentTarget.Swords, EnchantmentTarget.Bows),

    Chimera("ultimate_chimera", true, EnchantmentTarget.Swords),
    Combo("ultimate_combo", true, EnchantmentTarget.Swords),
    Duplex("ultimate_reiterate", true, EnchantmentTarget.Bows),
    FatalTempo("ultimate_fatal_tempo", true, EnchantmentTarget.Swords, EnchantmentTarget.Bows),
    Inferno("ultimate_inferno", true, EnchantmentTarget.Swords, EnchantmentTarget.Bows),
    OneForAll("ultimate_one_for_all", true, EnchantmentTarget.Swords),
    Rend("ultimate_rend", true, EnchantmentTarget.Bows),
    SoulEater("ultimate_soul_eater", true, EnchantmentTarget.Swords, EnchantmentTarget.Bows),
    Swarm("ultimate_swarm", true, EnchantmentTarget.Swords, EnchantmentTarget.Bows),
    UltimateJerry("ultimate_jerry", true, EnchantmentTarget.Swords),
    UltimateWise("ultimate_wise", true, EnchantmentTarget.Swords, EnchantmentTarget.Bows);

    enum class DeprecatedEnchantment(
        override val apiName: String,
        val ultimate: Boolean,
        vararg targets: EnchantmentTarget
    ) : Enchantment {
        Telekinesis(
            "telekinesis", false, EnchantmentTarget.Swords, EnchantmentTarget.Bows, EnchantmentTarget.Tools,
            EnchantmentTarget.FishingRods, EnchantmentTarget.Wands
        );
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