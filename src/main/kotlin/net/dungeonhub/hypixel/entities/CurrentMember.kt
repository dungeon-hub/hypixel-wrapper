package net.dungeonhub.hypixel.entities

import com.google.gson.JsonObject
import java.math.BigDecimal
import java.util.*

//TODO: Add the currencies and essence data to the general profile member? its nullable anyways, so it should be fine; tbf, it wouldnt make sense (since non-current members dont have it), but they're in the API
class CurrentMember(
    override val uuid: UUID,
    override val profile: JsonObject,
    override val leveling: MemberLeveling,
    override val playerData: MemberPlayerData,
    override val slayer: MemberSlayerData?,
    val currencies: List<Pair<CurrencyType, BigDecimal>>,
    val essence: JsonObject?,
    val dungeons: MemberDungeonsData?,
    override val raw: JsonObject
) : SkyblockProfileMember(uuid, profile, leveling, playerData, slayer, raw) {
    val coins
        get() = currencies.first { it.first == KnownCurrencyTypes.Coins }.second

    val motes
        get() = currencies.firstOrNull { it.first == KnownCurrencyTypes.Motes }?.second
}