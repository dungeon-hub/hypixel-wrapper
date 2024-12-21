package net.dungeonhub.hypixel.entities

import com.google.gson.JsonObject
import java.math.BigDecimal
import java.util.*

class CurrentMember(
    override val uuid: UUID,
    override val profile: JsonObject,
    val playerData: MemberPlayerData,
    val leveling: MemberLeveling,
    val currencies: List<Pair<CurrencyType, BigDecimal>>,
    val essence: JsonObject?,
    val dungeons: JsonObject?,
    override val raw: JsonObject
): SkyblockProfileMember(uuid, profile, raw) {
    val coins
        get() = currencies.first { it.first == KnownCurrencyTypes.Coins }.second

    val motes
        get() = currencies.firstOrNull { it.first == KnownCurrencyTypes.Motes }?.second
}