package net.dungeonhub.hypixel.entities.skyblock

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import net.dungeonhub.hypixel.entities.inventory.toMemberInventoryData
import net.dungeonhub.hypixel.entities.skyblock.currencies.KnownCurrencyTypes
import net.dungeonhub.hypixel.entities.skyblock.currencies.KnownCurrencyTypes.Companion.toCurrencyType
import net.dungeonhub.hypixel.entities.skyblock.currencies.KnownEssenceType
import net.dungeonhub.hypixel.entities.skyblock.dungeon.toDungeonsData
import net.dungeonhub.hypixel.entities.skyblock.misc.MemberPlayerData
import net.dungeonhub.hypixel.entities.skyblock.misc.toFairySoulData
import net.dungeonhub.hypixel.entities.skyblock.misc.toPlayerData
import net.dungeonhub.hypixel.entities.skyblock.pet.toPetsData
import net.dungeonhub.hypixel.entities.skyblock.rift.toRiftData
import net.dungeonhub.hypixel.entities.skyblock.slayer.MemberSlayerData
import net.dungeonhub.hypixel.entities.skyblock.slayer.toSlayerData
import net.dungeonhub.hypixel.entities.skyblock.stats.MemberPlayerStats
import net.dungeonhub.hypixel.entities.skyblock.stats.toPlayerStats
import net.dungeonhub.mojang.entity.toUUID
import net.dungeonhub.provider.GsonProvider
import net.dungeonhub.provider.getAsJsonObjectOrNull
import net.dungeonhub.provider.getAsJsonPrimitiveOrNull
import java.math.BigDecimal
import java.util.*

@GsonProvider.JsonType(
    "type", subtypes = [
        GsonProvider.JsonSubtype(CurrentMember::class, "current"),
        GsonProvider.JsonSubtype(PastMember::class, "past"),
        GsonProvider.JsonSubtype(PendingMember::class, "pending")
    ]
)
abstract class SkyblockProfileMember(
    open val uuid: UUID,
    val type: String,
    open val profile: JsonObject,
    open val leveling: MemberLeveling,
    open val playerData: MemberPlayerData?,
    open val playerStats: MemberPlayerStats?,
    open val slayer: MemberSlayerData?,
    open val raw: JsonObject
)

fun JsonObject.loadProfileMembers(): List<SkyblockProfileMember> {
    return entrySet().map {
        val uuid = if (has("player_id")) {
            UUID.fromString(getAsJsonPrimitive("player_id").asString)
        } else {
            it.key.toUUID()
        }
        val profileData = it.value.asJsonObject.getAsJsonObject("profile")

        if (profileData.getAsJsonObjectOrNull("deletion_notice") != null) {
            return@map PastMember(
                uuid,
                profileData,
                it.value.asJsonObject.getAsJsonObjectOrNull("leveling")?.toLeveling()
                    ?: defaultLeveling,
                it.value.asJsonObject.getAsJsonObjectOrNull("player_data")?.toPlayerData() ?: defaultPlayerData,
                it.value.asJsonObject.getAsJsonObjectOrNull("player_stats")?.toPlayerStats(),
                it.value.asJsonObject.getAsJsonObjectOrNull("slayer")?.toSlayerData(),
                it.value.asJsonObject.getAsJsonObjectOrNull("pets_data")?.toPetsData(),
                this
            )
        }

        val invitationConfirmation =
            profileData.getAsJsonObjectOrNull("coop_invitation")?.getAsJsonPrimitiveOrNull("confirmed")?.asBoolean

        if (invitationConfirmation == false) {
            return@map PendingMember(
                uuid,
                profileData,
                it.value.asJsonObject.getAsJsonObjectOrNull("leveling")?.toLeveling()
                    ?: defaultLeveling,
                it.value.asJsonObject.getAsJsonObjectOrNull("slayer")?.toSlayerData(),
                this
            )
        }

        return@map CurrentMember(
            uuid,
            profileData,
            it.value.asJsonObject.getAsJsonObjectOrNull("leveling")?.toLeveling()
                ?: defaultLeveling,
            it.value.asJsonObject.getAsJsonObjectOrNull("player_data")?.toPlayerData() ?: defaultPlayerData,
            it.value.asJsonObject.getAsJsonObjectOrNull("player_stats")?.toPlayerStats(),
            it.value.asJsonObject.getAsJsonObjectOrNull("slayer")?.toSlayerData(),
            it.value.asJsonObject.getAsJsonObjectOrNull("currencies")?.entrySet()
                ?.filter { currency -> currency.key != "essence" }?.associate { currency ->
                    currency.key.toCurrencyType() to currency.value.asBigDecimal
                } ?: mapOf(KnownCurrencyTypes.Coins to BigDecimal.ZERO),
            it.value.asJsonObject.getAsJsonObjectOrNull("currencies")?.entrySet()
                ?.firstOrNull { currency -> currency.key == "essence" }?.value?.asJsonObject?.entrySet()
                ?.associate { essence ->
                    KnownEssenceType.fromApiName(essence.key) to (essence.value.asJsonObject.getAsJsonPrimitiveOrNull("current")?.asInt
                        ?: 0)
                } ?: emptyMap(),
            it.value.asJsonObject.getAsJsonObjectOrNull("dungeons")?.toDungeonsData(),
            it.value.asJsonObject.getAsJsonObjectOrNull("accessory_bag_storage")?.toAccessoryBagStorage(),
            it.value.asJsonObject.getAsJsonObjectOrNull("fairy_soul")?.toFairySoulData(),
            it.value.asJsonObject.getAsJsonObjectOrNull("inventory")?.toMemberInventoryData(),
            it.value.asJsonObject.getAsJsonObjectOrNull("pets_data")?.toPetsData(),
            it.value.asJsonObject.getAsJsonObjectOrNull("rift")?.toRiftData(),
            this
        )
    }
}

private val defaultLeveling = MemberLeveling(
    0,
    JsonParser.parseString("{\"experience\":0}").asJsonObject
)
private val defaultPlayerData = MemberPlayerData(null, null, null, JsonObject())