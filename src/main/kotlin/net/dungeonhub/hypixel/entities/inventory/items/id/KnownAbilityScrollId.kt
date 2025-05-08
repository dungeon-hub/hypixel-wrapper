package net.dungeonhub.hypixel.entities.inventory.items.id

import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

enum class KnownAbilityScrollId(
    override val apiName: String,
    override val itemClass: ((SkyblockItem) -> SkyblockItem)
) : KnownSkyblockItemId, AbilityScrollId {
    AmberPowerScroll("AMBER_POWER_SCROLL"),
    AmethystPowerScroll("AMETHYST_POWER_SCROLL"),
    JadePowerScroll("JADE_POWER_SCROLL"),
    JasperPowerScroll("JASPER_POWER_SCROLL"),
    OpalPowerScroll("OPAL_POWER_SCROLL"),
    RubyPowerScroll("RUBY_POWER_SCROLL"),
    SapphirePowerScroll("SAPPHIRE_POWER_SCROLL"),
    TopazPowerScroll("TOPAZ_POWER_SCROLL");

    constructor(apiName: String) : this(apiName, { it })

    class UnknownAbilityScroll(override val apiName: String) : AbilityScrollId

    companion object {
        fun fromApiName(apiName: String): AbilityScrollId {
            return KnownAbilityScrollId.entries.firstOrNull { it.apiName == apiName }
                ?: UnknownAbilityScroll(apiName)
        }
    }
}