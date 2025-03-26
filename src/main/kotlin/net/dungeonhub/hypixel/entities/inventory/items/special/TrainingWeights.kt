package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem

class TrainingWeights(raw: NBTCompound) : SkyblockItem(raw) {
    val heldTime: Int
        get() = extraAttributes.getInt("trainingWeightsHeldTime", 0)
}