package net.dungeonhub.hypixel.entities

import com.google.gson.JsonObject
import java.util.*

class PastMember(
    override val uuid: UUID,
    override val profile: JsonObject,
    override val raw: JsonObject
) : SkyblockProfileMember(uuid, profile, raw)