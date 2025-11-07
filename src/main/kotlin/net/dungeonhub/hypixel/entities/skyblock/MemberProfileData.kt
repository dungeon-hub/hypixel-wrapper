package net.dungeonhub.hypixel.entities.skyblock

import com.google.gson.JsonObject
import net.dungeonhub.provider.getAsJsonObjectOrNull
import net.dungeonhub.provider.getAsJsonPrimitiveOrNull
import java.time.Instant

class MemberProfileData(
    val firstJoin: Instant?,
    val deletionNotice: JsonObject?,
    val coopInvitation: JsonObject?,
    val bankAccount: Double?,
    val cookieBuffActive: Boolean,
    val raw: JsonObject
)

fun JsonObject.toMemberProfileData(): MemberProfileData {
    return MemberProfileData(
        getAsJsonPrimitiveOrNull("first_join")?.asLong?.let(Instant::ofEpochMilli),
        getAsJsonObjectOrNull("deletion_notice"),
        getAsJsonObjectOrNull("coop_invitation"),
        getAsJsonPrimitiveOrNull("bank_account")?.asDouble,
        getAsJsonPrimitiveOrNull("cookie_buff_active")?.asBoolean ?: false,
        this
    )
}