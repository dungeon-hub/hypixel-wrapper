package net.dungeonhub.hypixel.entities.inventory.items.id

import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

interface KnownSkyblockItemId : SkyblockItemId {
    val name: String
    override val apiName: String
    val itemClass: ((SkyblockItem) -> SkyblockItem)

    companion object {
        val entries: List<KnownSkyblockItemId> = listOf(
            AccessoryItemId.entries,
            AdminItemId.entries,
            ArmorItemId.entries,
            ArrowItemId.entries,
            BrewingIngredientId.entries,
            BuggedItemId.entries,
            CollectionItemId.entries,
            ConsumableItemId.entries,
            CosmeticItemId.entries,
            DeployableItemId.entries,
            DisplayItemId.entries,
            DrillPartId.entries,
            DungeonItemId.entries,
            KnownDyeId.entries,
            EquipmentItemId.entries,
            ForgeableItemId.entries,
            GemstoneItemId.entries,
            ItemSackId.entries,
            MementoItemId.entries,
            MinionItemId.entries,
            MiscItemId.entries,
            MixinItemId.entries,
            KnownAbilityScrollId.entries,
            KnownEnrichmentId.entries,
            KnownPetItem.entries,
            KnownPetSkinId.entries,
            PotionItemId.entries,
            PowerStoneId.entries,
            ReforgeStoneId.entries,
            RiftItemId.entries,
            RiftTimecharmId.entries,
            KnownRodPartId.entries,
            ShearItemId.entries,
            ToolItemId.entries,
            TrophyFishItemId.entries,
            VanillaItemId.entries,
            WandItemId.entries,
            WeaponItemId.entries
        ).flatMap { it }

        fun fromApiName(apiName: String): SkyblockItemId {
            return entries.firstOrNull { it.apiName == apiName } ?: UnknownSkyblockItemId(apiName)
        }
    }
}