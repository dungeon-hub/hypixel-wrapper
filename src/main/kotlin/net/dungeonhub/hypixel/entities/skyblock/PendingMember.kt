package net.dungeonhub.hypixel.entities.skyblock

import com.google.gson.JsonObject
import net.dungeonhub.hypixel.entities.skyblock.slayer.MemberSlayerData
import java.util.*

data class PendingMember(
    override val uuid: UUID,
    override val profile: JsonObject,
    override val leveling: MemberLeveling,
    override val slayer: MemberSlayerData?,
    override val raw: JsonObject
) : SkyblockProfileMember(uuid, profile, leveling, null, null, slayer, raw) {
    override val type = "pending"
}