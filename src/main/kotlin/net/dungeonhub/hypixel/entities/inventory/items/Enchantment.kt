package net.dungeonhub.hypixel.entities.inventory.items

interface Enchantment {
    val apiName: String

    fun isUltimate(): Boolean =
        if (this is KnownEnchantment) {
            ultimate
        } else {
            apiName.startsWith("ultimate_")
        }
}