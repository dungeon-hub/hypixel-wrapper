package net.dungeonhub.hypixel.entities.inventory.items

interface ShinyItem : SkyblockItemFactory {
    override val uniqueName: String
        get() {
            return if (isShiny) {
                "${super.uniqueName}_SHINY"
            } else {
                super.uniqueName
            }
        }

    val isShiny: Boolean
        get() = extraAttributes.getInt("is_shiny", 0) > 0
}