package net.dungeonhub.hypixel.entities.skyblock

import com.google.gson.JsonObject
import net.dungeonhub.hypixel.entities.inventory.MemberInventory
import net.dungeonhub.hypixel.entities.skyblock.currencies.CurrencyType
import net.dungeonhub.hypixel.entities.skyblock.currencies.EssenceType
import net.dungeonhub.hypixel.entities.skyblock.currencies.KnownCurrencyTypes
import net.dungeonhub.hypixel.entities.skyblock.dungeon.MemberDungeonsData
import net.dungeonhub.hypixel.entities.skyblock.misc.FairySoulData
import net.dungeonhub.hypixel.entities.skyblock.misc.MemberPlayerData
import net.dungeonhub.hypixel.entities.skyblock.pet.MemberPetsData
import net.dungeonhub.hypixel.entities.skyblock.slayer.MemberSlayerData
import net.dungeonhub.hypixel.entities.skyblock.stats.MemberPlayerStats
import java.math.BigDecimal
import java.util.*

class CurrentMember(
    override val uuid: UUID,
    override val profile: JsonObject,
    override val leveling: MemberLeveling,
    override val playerData: MemberPlayerData,
    override val playerStats: MemberPlayerStats?,
    override val slayer: MemberSlayerData?,
    val currencies: Map<CurrencyType, BigDecimal>,
    val essence: Map<EssenceType, Int>,
    val dungeons: MemberDungeonsData?,
    val accessoryBag: AccessoryBagStorage?,
    val fairySoulData: FairySoulData?,
    val inventory: MemberInventory?,
    val petsData: MemberPetsData?,
    override val raw: JsonObject
) : SkyblockProfileMember(uuid, "current", profile, leveling, playerData, playerStats, slayer, raw) {
    val coins
        get() = currencies.entries.first { it.key == KnownCurrencyTypes.Coins }.value

    val motes
        get() = currencies.entries.firstOrNull { it.key == KnownCurrencyTypes.Motes }?.value
}