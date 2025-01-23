package net.dungeonhub.hypixel.entities.inventory

import com.google.gson.JsonObject
import me.nullicorn.nedit.NBTReader
import me.nullicorn.nedit.type.NBTCompound
import me.nullicorn.nedit.type.TagType
import net.dungeonhub.hypixel.entities.skyblock.dungeon.DungeonType
import net.dungeonhub.hypixel.entities.skyblock.dungeon.KnownDungeonType
import net.dungeonhub.hypixel.entities.skyblock.pet.Pet
import net.dungeonhub.hypixel.entities.skyblock.pet.toPet
import net.dungeonhub.provider.GsonProvider
import java.io.ByteArrayInputStream
import java.time.Instant
import java.util.*

//TODO add more fields
//TODO map id to item id
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
        get() = extraAttributes.getString("petInfo")?.let { GsonProvider.gson.fromJson(it, JsonObject::class.java) }
            ?.toPet()

    val attributes: Map<String, Int>
        get() = extraAttributes.getCompound("attributes")?.let {
            it.mapValues { attribute -> attribute.value as Int }
        } ?: emptyMap()

    val abilityScrolls: List<String>
        get() = extraAttributes.getList("ability_scroll")?.map { it.toString() } ?: emptyList()

    val dungeonLevel: Int
        get() = extraAttributes.getInt("dungeon_item_level", 0)

    val hasArtOfWar: Boolean
        get() = extraAttributes.getInt("art_of_war_count", 0) > 0

    val isShiny: Boolean
        get() = extraAttributes.getInt("is_shiny", 0) > 0

    val isRiftTransferred: Boolean
        get() = extraAttributes.getInt("rift_transferred", 0) > 0

    val hasJalapeno: Boolean
        get() = extraAttributes.getInt("jalapeno_count", 0) > 0

    val enrichment: String?
        get() = extraAttributes.getString("talisman_enrichment")

    val appliedDye: String?
        get() = extraAttributes.getString("dye_item")

    val appliedSkin: String?
        get() = extraAttributes.getString("skin")

    val polarvoid: Int
        get() = extraAttributes.getInt("polarvoid", 0)

    //TODO merge next 5 attributes
    val potionLevel: Int
        get() = extraAttributes.getInt("potion_level", 0)

    val potion: String?
        get() = extraAttributes.getString("potion")

    val potionType: String?
        get() = extraAttributes.getString("potion_type")

    val splash: Boolean
        get() = extraAttributes.getInt("splash", 0) > 0

    //TODO map to object
    val effects: NBTCompound?
        get() = extraAttributes.getCompound("effects")

    val expertiseKills: Int
        get() = extraAttributes.getInt("expertise_kills", 0)

    val lavaCreaturesKilled: List<String>
        get() = extraAttributes.getList("lava_creatures_killed")?.map { it.toString() } ?: emptyList()

    val brokenBlocks: Int
        get() = extraAttributes.getInt("blocksBroken", 0)

    val promisingPickaxeBreaks: Int
        get() = extraAttributes.getInt("promising_pickaxe_breaks", 0)

    val drillFuel: Int
        get() = extraAttributes.getInt("drill_fuel", 0)

    val statsBook: Int
        get() = extraAttributes.getInt("stats_book", 0)

    val championXp: Double
        get() = extraAttributes.getDouble("champion_combat_xp", 0.0)

    //TODO map to scroll type?
    val abilityScroll: String?
        get() = extraAttributes.getString("power_ability_scroll")

    val baseStatBoost: Int
        get() = extraAttributes.getInt("baseStatBoostPercentage", 0)

    val trapsDefused: Int
        get() = extraAttributes.getInt("trapsDefused", 0)

    val blazeTekkChannel: Int
        get() = extraAttributes.getInt("blazetekk_channel", -1)

    val blocksWalked: Int
        get() = extraAttributes.getInt("blocks_walked", 0)

    val compactBlocks: Int
        get() = extraAttributes.getInt("compact_blocks", 0)

    val raiderKills: Int
        get() = extraAttributes.getInt("raider_kills", 0)

    val bookWormBooks: Int
        get() = extraAttributes.getInt("bookworm_books", 0)

    val ranchersSpeed: Int
        get() = extraAttributes.getInt("ranchers_speed", 0)

    val thunderCharge: Int
        get() = extraAttributes.getInt("thunder_charge", 0)

    val toxophiliteExperience: Double
        get() = extraAttributes.getDouble("toxophilite_combat_xp", 0.0)

    //TODO map to enum
    val abiphoneModel: String?
        get() = extraAttributes.getString("model")

    val dungeonSkillRequirement: Pair<DungeonType, Int>?
        get() = extraAttributes.getString("dungeon_skill_req")?.split(":")?.let {
            KnownDungeonType.fromApiName(it.first().lowercase()) to Integer.valueOf(it.last())
        }

    val newYearCakeBagData: List<SkyblockItem>
        get() = extraAttributes.get("new_year_cake_bag_data")?.let {
            if (it is ByteArray) it.parseItemList() else emptyList()
        } ?: emptyList()

    val buildersWandData: List<SkyblockItem>
        get() = extraAttributes.get("builder's_wand_data")?.let {
            if (it is ByteArray) it.parseItemList() else emptyList()
        } ?: emptyList()
}

fun ByteArray.parseItemList(): List<SkyblockItem> {
    val list = NBTReader.read(ByteArrayInputStream(this))
    if (list.containsTag("i", TagType.LIST)) {
        return list.getList("i").mapNotNull { item ->
            if (item is NBTCompound && item.isSkyblockItem()) SkyblockItem(item) else null
        }
    }
    return emptyList()
}

fun NBTCompound.isSkyblockItem(): Boolean {
    if (!isValidItem()) return false

    val tag = getCompound("tag") ?: return false

    return tag.getCompound("ExtraAttributes")?.getString("id") != null
}