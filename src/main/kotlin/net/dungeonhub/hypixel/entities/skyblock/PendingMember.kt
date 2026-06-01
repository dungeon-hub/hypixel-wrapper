package net.dungeonhub.hypixel.entities.skyblock

import net.dungeonhub.hypixel.entities.skyblock.slayer.MemberSlayerData
import java.util.*

class PendingMember(
    override val uuid: UUID,
    override val profile: MemberProfileData,
    override val leveling: MemberLeveling,
    override val slayer: MemberSlayerData?
) : SkyblockProfileMember(uuid, "pending", profile, leveling, null, null, slayer)