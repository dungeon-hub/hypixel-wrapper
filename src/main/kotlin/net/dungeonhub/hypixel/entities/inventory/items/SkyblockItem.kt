package net.dungeonhub.hypixel.entities.inventory.items

import me.nullicorn.nedit.NBTReader
import me.nullicorn.nedit.type.NBTCompound
import me.nullicorn.nedit.type.TagType
import net.dungeonhub.hypixel.entities.inventory.*
import net.dungeonhub.hypixel.entities.skyblock.dungeon.DungeonType
import net.dungeonhub.hypixel.entities.skyblock.dungeon.KnownDungeonType
import java.io.ByteArrayInputStream
import java.time.Instant
import java.util.*

//TODO add more fields
//TODO map attribute type
open class SkyblockItem(raw: NBTCompound) : ItemStack(raw), SkyblockItemFactory {
    val id: SkyblockItemId = KnownSkyblockItemId.fromApiName(extraAttributes.getString("id"))

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

    //TODO check / migrate all below


    val etherWarp: Boolean
        get() = extraAttributes.getByte("ethermerge", 0) == 1.toByte()

    val transmissionTuners: Int
        get() = extraAttributes.getInt("tuned_transmission", 0)

    val manaDisintegrators: Int
        get() = extraAttributes.getInt("mana_disintegrator_count", 0)

    val gems: ItemGemsData?
        get() = extraAttributes.getCompound("gems")?.toGemsData()

    val runes: Map<String, Int>
        get() = extraAttributes.getCompound("runes")?.let {
            it.mapValues { rune -> rune.value as Int }
        } ?: emptyMap()

    val starAmount: Int
        get() = extraAttributes.getInt("upgrade_level", 0)

    val dungeonItem: Boolean
        get() = extraAttributes.getByte("dungeon_item", -1) == 1.toByte()

    val anvilUses: Int
        get() = extraAttributes.getInt("anvil_uses", 0)

    val attributes: Map<String, Int>
        get() = extraAttributes.getCompound("attributes")?.let {
            it.mapValues { attribute -> attribute.value as Int }
        } ?: emptyMap()

    val dungeonLevel: Int
        get() = extraAttributes.getInt("dungeon_item_level", 0)

    val hasArtOfWar: Boolean
        get() = extraAttributes.getInt("art_of_war_count", 0) > 0

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

    val newYearCakeBagData: List<ItemStack>
        get() = extraAttributes.get("new_year_cake_bag_data")?.let {
            if (it is ByteArray) it.parseItemList() else emptyList()
        } ?: emptyList()

    companion object {
        fun fromNbtCompound(compound: NBTCompound): SkyblockItem? {
            if (!compound.isSkyblockItem()) {
                return null
            }

            val skyblockItem = SkyblockItem(compound)

            return if (skyblockItem.id is KnownSkyblockItemId) skyblockItem.id.itemClass(skyblockItem) else skyblockItem
        }
    }
}

interface SkyblockItemFactory {
    val raw: NBTCompound

    val extraAttributes: NBTCompound
}

fun ByteArray.parseItemList(): List<ItemStack> {
    val list = NBTReader.read(ByteArrayInputStream(this))
    if (list.containsTag("i", TagType.LIST)) {
        return list.getList("i").mapNotNull { item ->
            if (item is NBTCompound) item.toItem() else null
        }
    }
    return emptyList()
}

/*fun NBTCompound.isSkyblockItem(): Boolean {
    if (!isValidItem()) return false

    val tag = getCompound("tag") ?: return false

    return tag.getCompound("ExtraAttributes")?.getString("id") != null
}*/

fun NBTCompound.isSkyblockItem(): Boolean {
    if (!isValidItem()) return false

    val tag = getCompound("tag") ?: return false

    val ea = HashMap(tag.getCompound("ExtraAttributes") ?: HashMap())

    ea.remove("id")
    ea.remove("blocksBroken")
    ea.remove("drill_fuel")
    ea.remove("champion_combat_xp")
    ea.remove("uuid")
    ea.remove("stats_book")
    ea.remove("bookworm_books")
    ea.remove("thunder_charge")
    ea.remove("trapsDefused")
    ea.remove("timestamp")
    ea.remove("new_year_cake_bag_data")
    ea.remove("ranchers_speed")
    ea.remove("raider_kills")
    ea.remove("builder's_wand_data")
    ea.remove("blazetekk_channel")
    ea.remove("promising_pickaxe_breaks")
    ea.remove("blocks_walked")
    ea.remove("ethermerge")
    ea.remove("compact_blocks")
    ea.remove("tuned_transmission")
    ea.remove("power_ability_scroll")
    ea.remove("baseStatBoostPercentage")
    ea.remove("rarity_upgrades")
    ea.remove("originTag")
    ea.remove("mana_disintegrator_count")
    ea.remove("donated_museum")
    ea.remove("modifier")
    ea.remove("hot_potato_count")
    ea.remove("enchantments")
    ea.remove("dungeon_item")
    ea.remove("runes")
    ea.remove("upgrade_level")
    ea.remove("anvil_uses")
    ea.remove("gems")
    ea.remove("petInfo")
    ea.remove("ability_scroll")
    ea.remove("dungeon_item_level")
    ea.remove("art_of_war_count")
    ea.remove("attributes")
    ea.remove("toxophilite_combat_xp")
    ea.remove("is_shiny")
    ea.remove("rift_transferred")
    ea.remove("jalapeno_count")
    ea.remove("talisman_enrichment")
    ea.remove("bossId")
    ea.remove("spawnedFor")
    ea.remove("skin")
    ea.remove("dye_item")
    ea.remove("polarvoid")
    ea.remove("potion_level")
    ea.remove("potion")
    ea.remove("potion_type")
    ea.remove("splash")
    ea.remove("effects")
    ea.remove("expertise_kills")
    ea.remove("lava_creatures_killed")
    ea.remove("dungeon_skill_req")

    if (ea.isNotEmpty()) {
        //println(ea)
    }

    return tag.getCompound("ExtraAttributes")?.getString("id") != null
}