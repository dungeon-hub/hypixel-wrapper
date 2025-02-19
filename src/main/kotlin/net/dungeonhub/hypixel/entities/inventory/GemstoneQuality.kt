package net.dungeonhub.hypixel.entities.inventory

enum class GemstoneQuality(val apiName: String, val rarity: SkyblockRarity) {
    Rough("ROUGH", SkyblockRarity.Common),
    Flawed("FLAWED", SkyblockRarity.Uncommon),
    Fine("FINE", SkyblockRarity.Rare),
    Flawless("FLAWLESS", SkyblockRarity.Epic),
    Perfect("PERFECT", SkyblockRarity.Legendary);

    companion object {
        fun fromApiName(name: String): GemstoneQuality? = entries.firstOrNull { it.apiName == name }
    }
}