package net.dungeonhub.hypixel.entities.inventory.items.id

import net.dungeonhub.hypixel.entities.inventory.items.Deployable
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

enum class DeployableItemId(override val apiName: String, override val itemClass: ((SkyblockItem) -> SkyblockItem)) :
    KnownSkyblockItemId {
    RadiantPowerOrb("RADIANT_POWER_ORB", { Deployable(it.raw) }),
    ManaFluxPowerOrb("MANA_FLUX_POWER_ORB", { Deployable(it.raw) }),
    OverfluxPowerOrb("OVERFLUX_POWER_ORB", { Deployable(it.raw) }),
    PlasmafluxPowerOrb("PLASMAFLUX_POWER_ORB", { Deployable(it.raw) }),
    WarningFlare("WARNING_FLARE", { Deployable(it.raw) }),
    AlertFlare("ALERT_FLARE", { Deployable(it.raw) }),
    SOSFlare("SOS_FLARE", { Deployable(it.raw) }),
    Umberella("UMBERELLA", { SkyblockItem(it.raw) })
}