package net.dungeonhub.hypixel.entities

import com.google.gson.JsonObject
import java.util.*

class PastMember(
    override val uuid: UUID,
    override val profile: JsonObject,
    override val leveling: MemberLeveling,
    override val playerData: MemberPlayerData,
    override val slayer: MemberSlayerData?,
    override val raw: JsonObject
) : SkyblockProfileMember(uuid, profile, leveling, playerData, slayer, raw)