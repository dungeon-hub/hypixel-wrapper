package net.dungeonhub.hypixel.entities.inventory

import com.google.gson.JsonObject
import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.Pet
import net.dungeonhub.hypixel.entities.toPet
import net.dungeonhub.provider.GsonProvider
import java.time.Instant
import java.util.*

//TODO add more fields
//TODO map id to item id
//TODO map origin tag
//TODO map reforge data
//TODO map enchantment data
//TODO map attribute type
class SkyblockItem(raw: NBTCompound) : ItemStack(raw) {
    val id: String
        get() = extraAttributes.getString("id")

    val uuid: UUID?
        get() = extraAttributes.getString("uuid")?.let { UUID.fromString(it) }

    val originTag: String?
        get() = extraAttributes.getString("originTag")

    val timestamp: Instant?
        get() = extraAttributes.getLong("timestamp", -1).takeIf { it != -1L }?.let { Instant.ofEpochMilli(it) }

    val rarityUpgraded: Boolean
        get() = extraAttributes.getInt("rarity_upgrades", 0) == 1

    val museumDonated: Boolean
        get() = extraAttributes.getByte("donated_museum", 0) == 1.toByte()

    val etherWarp: Boolean
        get() = extraAttributes.getByte("ethermerge", 0) == 1.toByte()

    val transmissionTuners: Int
        get() = extraAttributes.getInt("tuned_transmission", 0)

    val manaDisintegrators: Int
        get() = extraAttributes.getInt("mana_disintegrator_count", 0)

    val gems: ItemGemsData?
        get() = extraAttributes.getCompound("gems")?.toGemsData()

    val enchantments: Map<String, Int>
        get() = extraAttributes.getCompound("enchantments")?.let {
            it.mapValues { enchantment -> enchantment.value as Int }
        } ?: emptyMap()

    val runes: Map<String, Int>
        get() = extraAttributes.getCompound("runes")?.let {
            it.mapValues { rune -> rune.value as Int }
        } ?: emptyMap()

    val reforge: String?
        get() = extraAttributes.getString("modifier")

    val starAmount: Int
        get() = extraAttributes.getInt("upgrade_level", 0)

    val hotPotatoes: Int
        get() = extraAttributes.getInt("hot_potato_count", 0)

    val dungeonItem: Boolean
        get() = extraAttributes.getByte("dungeon_item", -1) == 1.toByte()

    val anvilUses: Int
        get() = extraAttributes.getInt("anvil_uses", 0)

    val petInfo: Pet?
        get() = extraAttributes.getString("petInfo")?.let { GsonProvider.gson.fromJson(it, JsonObject::class.java) }?.toPet()

    val attributes: Map<String, Int>
        get() = extraAttributes.getCompound("attributes")?.let {
            it.mapValues { attribute -> attribute.value as Int }
        } ?: emptyMap()
}

fun NBTCompound.isSkyblockItem(): Boolean {
    if (!isValidItem()) return false

    val tag = getCompound("tag") ?: return false

    return tag.getCompound("ExtraAttributes")?.getString("id") != null
}