package net.dungeonhub.hypixel.entities.inventory.items

interface ItemWithAttributes : SkyblockItemFactory {
    //TODO map to attribute type
    val attributes: Map<String, Int>
        get() = extraAttributes.getCompound("attributes")?.let {
            it.mapValues { attribute -> attribute.value as Int }
        } ?: emptyMap()
}