package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound

class EditorsPencil(raw: NBTCompound) : AdminGiftedItem(raw) {
    override val recipientName: String?
        get() = extraAttributes.getString("user")

    override val sender: String?
        get() = extraAttributes.getString("sender")
}