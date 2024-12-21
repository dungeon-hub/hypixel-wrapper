package net.dungeonhub.hypixel.entities

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import net.dungeonhub.hypixel.entities.KnownCurrencyTypes.Companion.toCurrencyType
import net.dungeonhub.mojang.entity.toUUIDUnsafe
import net.dungeonhub.provider.getAsJsonObjectOrNull
import net.dungeonhub.provider.getAsJsonPrimitiveOrNull
import java.math.BigDecimal
import java.util.*

abstract class SkyblockProfileMember(
    open val uuid: UUID,
    open val profile: JsonObject,
    open val raw: JsonObject
)

fun JsonObject.loadProfileMembers(): List<SkyblockProfileMember> {
    return entrySet().map {
        val uuid = if (has("player_id")) {
            UUID.fromString(getAsJsonPrimitive("player_id").asString)
        } else {
            it.key.toUUIDUnsafe()
        }
        val profileData = it.value.asJsonObject.getAsJsonObject("profile")

        if(profileData.getAsJsonObjectOrNull("deletion_notice") != null) {
            return@map PastMember(
                uuid,
                profileData,
                this
            )
        }

        val invitationConfirmation = profileData.getAsJsonObjectOrNull("coop_invitation")?.getAsJsonPrimitiveOrNull("confirmed")?.asBoolean

        if(invitationConfirmation == false) {
            return@map PendingMember(
                uuid,
                profileData,
                this
            )
        }

        return@map CurrentMember(
            uuid,
            profileData,
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

private val defaultLeveling = MemberLeveling(
    0,
    JsonParser.parseString("{\"experience\":0}").asJsonObject
)