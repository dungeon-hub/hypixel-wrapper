package net.dungeonhub.hypixel.entities.inventory.items.id

import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem
import net.dungeonhub.hypixel.entities.skyblock.trophyfish.TrophyFishRarity
import net.dungeonhub.hypixel.entities.skyblock.trophyfish.TrophyFishType

class TrophyFishItemId(
    val trophyFishType: TrophyFishType,
    val rarity: TrophyFishRarity,
    override val itemClass: (SkyblockItem) -> SkyblockItem
) : KnownSkyblockItemId {
    override val name: String
        get() = trophyFishType.name + rarity.name

    override val apiName
        get() = trophyFishType.apiName + "_" + rarity.apiName

    companion object {
        fun fromTypeAndRarity(type: TrophyFishType, rarity: TrophyFishRarity): TrophyFishItemId {
            return TrophyFishItemId(type, rarity, { it })
        }

        val entries
            get() = TrophyFishType.entries.flatMap { fish: TrophyFishType ->
                TrophyFishRarity.entries.map { rarity: TrophyFishRarity ->
                    fish to rarity
                }
            }.map { (fish, rarity) -> fromTypeAndRarity(fish, rarity) }
    }
}