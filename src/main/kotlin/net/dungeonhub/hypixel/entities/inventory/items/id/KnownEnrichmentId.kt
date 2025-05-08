package net.dungeonhub.hypixel.entities.inventory.items.id

import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

enum class KnownEnrichmentId(
    override val apiName: String,
    override val appliedId: String,
    override val itemClass: ((SkyblockItem) -> SkyblockItem),
) : KnownSkyblockItemId, EnrichmentId {
    AttackSpeedEnrichment("TALISMAN_ENRICHMENT_ATTACK_SPEED", "attack_speed"),
    CriticalChanceEnrichment("TALISMAN_ENRICHMENT_CRITICAL_CHANCE", "critical_chance"),
    CriticalDamageEnrichment("TALISMAN_ENRICHMENT_CRITICAL_DAMAGE", "critical_damage"),
    DefenseEnrichment("TALISMAN_ENRICHMENT_DEFENSE", "defense"),
    FerocityEnrichment("TALISMAN_ENRICHMENT_FEROCITY", "ferocity"),
    HealthEnrichment("TALISMAN_ENRICHMENT_HEALTH", "health"),
    IntelligenceEnrichment("TALISMAN_ENRICHMENT_INTELLIGENCE", "intelligence"),
    MagicFindEnrichment("TALISMAN_ENRICHMENT_MAGIC_FIND", "magic_find"),
    SeaCreatureChanceEnrichment("TALISMAN_ENRICHMENT_SEA_CREATURE_CHANCE", "sea_creature_chance"),
    SpeedEnrichment("TALISMAN_ENRICHMENT_WALK_SPEED", "walk_speed"),
    StrengthEnrichment("TALISMAN_ENRICHMENT_STRENGTH", "strength");

    constructor(apiName: String, appliedId: String) : this(apiName, appliedId, { it })

    class UnknownEnrichment(override val appliedId: String) : EnrichmentId

    companion object {
        fun fromAppliedName(appliedId: String): EnrichmentId {
            return KnownEnrichmentId.entries.firstOrNull { it.appliedId.equals(appliedId, true) }
                ?: UnknownEnrichment(appliedId)
        }
    }
}