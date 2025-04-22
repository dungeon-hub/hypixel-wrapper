package net.dungeonhub.hypixel.entities.inventory.items

import me.nullicorn.nedit.type.NBTCompound

interface NecromancyItem : SkyblockItemFactory {
    val souls: List<NecromancerSoul>
        get() = extraAttributes.getList("necromancer_souls")?.mapNotNull { it as? NBTCompound }
            ?.map { NecromancerSoul.fromCompound(it) } ?: emptyList()

    class NecromancerSoul(
        val mobId: String,
        val modeId: String?,
        val instanceId: String?
    ) {
        companion object {
            fun fromCompound(raw: NBTCompound): NecromancerSoul {
                return NecromancerSoul(
                    raw.getString("mob_id"),
                    raw.getString("dropped_mode_id"),
                    raw.getString("dropped_instance_id")
                )
            }
        }
    }
}