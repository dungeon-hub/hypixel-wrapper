package net.dungeonhub.hypixel.entities.inventory.items

import me.nullicorn.nedit.NBTReader
import me.nullicorn.nedit.type.NBTCompound
import me.nullicorn.nedit.type.TagType
import net.dungeonhub.hypixel.entities.inventory.ItemStack
import net.dungeonhub.hypixel.entities.inventory.isValidItem
import net.dungeonhub.hypixel.entities.inventory.toItem
import net.dungeonhub.hypixel.entities.skyblock.dungeon.DungeonType
import net.dungeonhub.hypixel.entities.skyblock.dungeon.KnownDungeonType
import java.io.ByteArrayInputStream
import java.time.Instant
import java.util.*

//TODO add more fields that were missing
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

    val soulbound: Boolean
        get() = extraAttributes.getByte("soulbound", 0) == 1.toByte()

    val isRiftTransferred: Boolean
        get() = extraAttributes.getInt("rift_transferred", 0) > 0

    //TODO check / migrate all below

    val dungeonLevel: Int
        get() = extraAttributes.getInt("dungeon_item_level", 0)

    val polarvoid: Int
        get() = extraAttributes.getInt("polarvoid", 0)

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

    val bookWormBooks: Int
        get() = extraAttributes.getInt("bookworm_books", 0)

    val ranchersSpeed: Int
        get() = extraAttributes.getInt("ranchers_speed", 0)

    val dungeonSkillRequirement: Pair<DungeonType, Int>?
        get() = extraAttributes.getString("dungeon_skill_req")?.split(":")?.let {
            KnownDungeonType.fromApiName(it.first().lowercase()) to Integer.valueOf(it.last())
        }

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
    ea.remove("expertise_kills")
    ea.remove("lava_creatures_killed")
    ea.remove("dungeon_skill_req")
    ea.remove("artOfPeaceApplied")

    if (ea.isNotEmpty()) {
        //println(ea)
    }

    return tag.getCompound("ExtraAttributes")?.getString("id") != null
}