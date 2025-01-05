package net.dungeonhub.hypixel.entities

import com.google.gson.JsonObject
import net.dungeonhub.hypixel.entities.inventory.MemberInventory
import java.math.BigDecimal
import java.util.*

class CurrentMember(
    override val uuid: UUID,
    override val profile: JsonObject,
    override val leveling: MemberLeveling,
    override val playerData: MemberPlayerData,
    override val slayer: MemberSlayerData?,
    val currencies: Map<CurrencyType, BigDecimal>,
    val essence: Map<EssenceType, Int>,
    val dungeons: MemberDungeonsData?,
    val accessoryBag: AccessoryBagStorage?,
    val fairySoulData: FairySoulData?,
    val inventory: MemberInventory?,
    override val raw: JsonObject
) : SkyblockProfileMember(uuid, "current", profile, leveling, playerData, slayer, raw) {
    val coins
        get() = currencies.entries.first { it.key == KnownCurrencyTypes.Coins }.value

    val motes
        get() = currencies.entries.firstOrNull { it.key == KnownCurrencyTypes.Motes }?.value
}