package net.dungeonhub.hypixel.entities.inventory.items

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.id.EnrichmentId
import net.dungeonhub.hypixel.entities.inventory.items.id.KnownEnrichmentId

open class Accessory(raw: NBTCompound) : SkyblockItem(raw), ReforgeableItem {
    val enrichment: EnrichmentId?
        get() = extraAttributes.getString("talisman_enrichment")?.let(KnownEnrichmentId::fromAppliedName)
}