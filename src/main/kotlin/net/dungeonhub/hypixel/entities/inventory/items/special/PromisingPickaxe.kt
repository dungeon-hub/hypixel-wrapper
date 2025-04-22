package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.PromisingTool

class PromisingPickaxe(raw: NBTCompound) : MiningTool(raw), PromisingTool {
    override val blocksBroken: Int
        get() = extraAttributes.getInt("promising_pickaxe_breaks", 0)
}