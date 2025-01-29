package net.dungeonhub.hypixel.entities.inventory.items

interface EnchantableItem : SkyblockItemFactory {
    val enchantments: Map<Enchantment, Int>
        get() = extraAttributes.getCompound("enchantments")?.let {
            it.entries.associate { (key, value) ->
                KnownEnchantment.fromApiName(key.lowercase()) to value as Int
            }
        } ?: emptyMap()
}