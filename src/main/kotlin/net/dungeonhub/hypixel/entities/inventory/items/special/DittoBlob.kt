package net.dungeonhub.hypixel.entities.inventory.items.special

import me.nullicorn.nedit.type.NBTCompound
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem
import net.dungeonhub.hypixel.entities.inventory.items.id.KnownSkyblockItemId
import net.dungeonhub.hypixel.entities.inventory.items.id.SkyblockItemId

class DittoBlob(raw: NBTCompound) : SkyblockItem(raw) {
    val originalItemId: SkyblockItemId?
        get() = extraAttributes.getString("ditto_og_item_id")?.let { KnownSkyblockItemId.fromApiName(it) }

    val appliedSkin: String?
        get() = extraAttributes.getString("ditto_applied_skin")

    val appliedSkinSignature: String?
        get() = extraAttributes.getString("ditto_applied_skin_signature")
}