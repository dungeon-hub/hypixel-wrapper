package net.dungeonhub.hypixel.entities

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import net.dungeonhub.hypixel.entities.KnownCurrencyTypes.Companion.toCurrencyType
import net.dungeonhub.mojang.entity.toUUIDUnsafe
import net.dungeonhub.provider.getAsJsonObjectOrNull
import java.math.BigDecimal
import java.util.*

class SkyblockProfileMember(
    val uuid: UUID,
    val playerData: MemberPlayerData,
    val leveling: MemberLeveling,
    val currencies: List<Pair<CurrencyType, BigDecimal>>,
    val essence: JsonObject?,
    val dungeons: JsonObject?,
    val raw: JsonObject
) {
    val coins
        get() = currencies.first { it.first == KnownCurrencyTypes.Coins }.second

    val motes
        get() = currencies.firstOrNull { it.first == KnownCurrencyTypes.Motes }?.second
}

fun JsonObject.loadProfileMembers(): List<SkyblockProfileMember> {
    val defaultLeveling = MemberLeveling(
        0,
        JsonParser.parseString("{\"experience\":0}").asJsonObject
    )

    return entrySet().map {
        SkyblockProfileMember(
            if (has("player_id"))
                UUID.fromString(getAsJsonPrimitive("player_id").asString)
            else
                it.key.toUUIDUnsafe(),
            it.value.asJsonObject.getAsJsonObject("player_data").toPlayerData(),
            it.value.asJsonObject.getAsJsonObjectOrNull("leveling")?.toLeveling()
                ?: defaultLeveling,
            it.value.asJsonObject.getAsJsonObjectOrNull("currencies")?.entrySet()
                ?.filter { currency -> currency.key != "essence" }?.map { currency ->
                currency.key.toCurrencyType() to currency.value.asBigDecimal
            } ?: listOf(KnownCurrencyTypes.Coins to BigDecimal.ZERO),
            it.value.asJsonObject.getAsJsonObjectOrNull("currencies")?.entrySet()
                ?.firstOrNull { currency -> currency.key == "essence" }?.value?.asJsonObject,
            it.value.asJsonObject.getAsJsonObjectOrNull("dungeons"),
            this
        )
    }
}