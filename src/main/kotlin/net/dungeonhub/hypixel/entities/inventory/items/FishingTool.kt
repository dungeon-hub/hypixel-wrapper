package net.dungeonhub.hypixel.entities.inventory.items

interface FishingTool : SkyblockItemFactory, ItemWithCombatBooks {
    val expertiseKills: Int?
        get() = extraAttributes.getInt("expertise_kills", -1).takeIf { it != -1 }

    /**
     * This isn't available for all rods, it looks like only the Yeti and Auger Rod currently have this field.
     */
    val fishesCaught: Int?
        get() = extraAttributes.getInt("fishes_caught", -1).takeIf { it != -1 }

    /**
     * This isn't available for all lava rods, it looks like only the Magma Rod (and related) have this field.
     */
    val lavaCreaturesKilled: List<String>
        get() = extraAttributes.getList("lava_creatures_killed")?.map { it as String } ?: emptyList()
}