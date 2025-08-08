package net.dungeonhub.hypixel.entities.skyblock

import com.google.gson.JsonObject
import net.dungeonhub.hypixel.entities.skyblock.slayer.MemberSlayerData
import java.util.*

class PendingMember(
    override val uuid: UUID,
    override val profile: MemberProfileData,
    override val leveling: MemberLeveling,
    override val slayer: MemberSlayerData?,
    override val raw: JsonObject
) : SkyblockProfileMember(uuid, "pending", profile, leveling, null, null, slayer, raw)