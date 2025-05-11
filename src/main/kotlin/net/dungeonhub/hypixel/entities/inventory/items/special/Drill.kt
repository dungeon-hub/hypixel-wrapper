package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.PowderCoatableItem
import net.dungeonhub.hypixel.entities.inventory.items.id.DrillPartId
import net.dungeonhub.hypixel.entities.inventory.items.id.KnownSkyblockItemId
import net.dungeonhub.hypixel.entities.inventory.items.id.SkyblockItemId

open class Drill(raw: NBTCompound) : MiningTool(raw), PowderCoatableItem {
    val fuel: Int
        get() = extraAttributes.getInt("drill_fuel", 0)

    val polarvoid: Int
        get() = extraAttributes.getInt("polarvoid", 0)

    /**
     * @see DrillPartId
     */
    val upgradeModule: SkyblockItemId?
        get() = extraAttributes.getString("drill_part_upgrade_module")
            ?.let { KnownSkyblockItemId.fromApiName(it.uppercase()) }

    /**
     * @see DrillPartId
     */
    val engine: SkyblockItemId?
        get() = extraAttributes.getString("drill_part_engine")
            ?.let { KnownSkyblockItemId.fromApiName(it.uppercase()) }

    /**
     * @see DrillPartId
     */
    val fuelTank: SkyblockItemId?
        get() = extraAttributes.getString("drill_part_fuel_tank")
            ?.let { KnownSkyblockItemId.fromApiName(it.uppercase()) }
}