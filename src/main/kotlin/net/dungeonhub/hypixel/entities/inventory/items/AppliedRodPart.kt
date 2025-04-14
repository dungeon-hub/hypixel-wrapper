package net.dungeonhub.hypixel.entities.inventory.items

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.id.KnownRodPartId
import net.dungeonhub.hypixel.entities.inventory.items.id.RodPartId
import net.dungeonhub.mojang.entity.toUUID
import java.util.UUID

class AppliedRodPart(val part: RodPartId, val uuid: UUID?, val donatedMuseum: Boolean) {
    companion object {
        fun NBTCompound.toRodPart(): AppliedRodPart {
            return AppliedRodPart(
                getString("part").let { KnownRodPartId.fromApiName(it) },
                getString("uuid")?.toUUID(),
                getInt("donated_museum", 0) == 1
            )
        }
    }
}