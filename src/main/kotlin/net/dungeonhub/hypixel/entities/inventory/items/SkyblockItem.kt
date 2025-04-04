package net.dungeonhub.hypixel.entities.inventory.items

import me.nullicorn.nedit.NBTReader
import me.nullicorn.nedit.type.NBTCompound
import me.nullicorn.nedit.type.TagType
import net.dungeonhub.hypixel.entities.inventory.ItemStack
import net.dungeonhub.hypixel.entities.inventory.isValidItem
import net.dungeonhub.hypixel.entities.inventory.items.id.KnownSkyblockItemId
import net.dungeonhub.hypixel.entities.inventory.items.id.SkyblockItemId
import net.dungeonhub.hypixel.entities.inventory.toItem
import java.io.ByteArrayInputStream
import java.time.Instant
import java.util.*

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

    // Those fields aren't as common, but they also aren't important
    val yearClaimedFromGiftbag: Int?
        get() = extraAttributes.getInt("giftbag_claim", -1).takeIf { it != -1 }

    val raffleWin: String?
        get() = extraAttributes.getString("raffle_win")

    val raffleYear: Int?
        get() = extraAttributes.getInt("raffle_year", -1).takeIf { it != -1 }

    val yearObtained: Int?
        get() = extraAttributes.getInt("yearObtained", -1).takeIf { it != -1 }

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

fun NBTCompound.isSkyblockItem(): Boolean {
    if (!isValidItem()) return false

    val tag = getCompound("tag") ?: return false

    return tag.getCompound("ExtraAttributes")?.getString("id") != null
}