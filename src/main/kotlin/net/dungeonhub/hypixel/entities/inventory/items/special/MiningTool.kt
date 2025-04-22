package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.Gear
import net.dungeonhub.hypixel.entities.inventory.items.MiningToolItem

open class MiningTool(raw: NBTCompound) : Gear(raw), MiningToolItem