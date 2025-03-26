package net.dungeonhub.hypixel.entities.inventory.items

interface ItemWithRune : SkyblockItemFactory {
    //TODO map to rune type
    val runes: Map<String, Int>
        get() = extraAttributes.getCompound("runes")?.let {
            it.mapValues { rune -> rune.value as Int }
        } ?: emptyMap()
}