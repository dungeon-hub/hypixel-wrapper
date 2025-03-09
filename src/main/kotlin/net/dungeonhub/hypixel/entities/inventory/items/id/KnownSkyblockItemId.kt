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
            ConsumableItemId.entries,
            CosmeticItemId.entries,
            DeployableItemId.entries,
            DrillPartId.entries,
            DungeonItemId.entries,
            EquipmentItemId.entries,
            ForgeableItemId.entries,
            ItemSackId.entries,
            MementoItemId.entries,
            MinionItemId.entries,
            MiscItemId.entries,
            MixinItemId.entries,
            PetItemId.entries,
            PotionItemId.entries,
            PowerStoneId.entries,
            ReforgeStoneId.entries,
            RiftItemId.entries,
            RiftTimecharmId.entries,
            ShearItemId.entries,
            ToolItemId.entries,
            VanillaItemId.entries,
            WandItemId.entries,
            WeaponItemId.entries
        ).flatMap { it }

        fun fromApiName(apiName: String): SkyblockItemId {
            return entries.firstOrNull { it.apiName == apiName } ?: UnknownSkyblockItemId(apiName)
        }
    }
}