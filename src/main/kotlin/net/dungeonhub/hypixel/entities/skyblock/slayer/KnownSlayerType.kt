package net.dungeonhub.hypixel.entities.skyblock.slayer

enum class KnownSlayerType(override val apiName: String, val maxBossTier: Int, val requiredExperience: List<Int>): SlayerType {
    Zombie("zombie", 5, listOf(5, 15, 200, 1000, 5000, 20000, 100000, 400000, 1000000)),
    Spider("spider", 5, listOf(5, 25, 200, 1000, 5000, 20000, 100000, 400000, 1000000)),
    Wolf("wolf", 4, listOf(10, 30, 250, 1500, 5000, 20000, 100000, 400000, 1000000)),
    Enderman("enderman", 4, listOf(10, 30, 250, 1500, 5000, 20000, 100000, 400000, 1000000)),
    Blaze("blaze", 4, listOf(10, 30, 250, 1500, 5000, 20000, 100000, 400000, 1000000)),
    Vampire("vampire", 5, listOf(20, 75, 240, 840, 2400));

    val maxLevel = requiredExperience.size

    fun toLevel(xp: Int): Int {
        for((index, requiredXp) in requiredExperience.withIndex()) {
            if(xp < requiredXp) return index
        }

        return requiredExperience.size
    }

    class UnknownSlayerType(override val apiName: String): SlayerType

    companion object {
        fun fromApiName(name: String): SlayerType {
            return KnownSlayerType.entries.firstOrNull { it.apiName == name }
                ?: UnknownSlayerType(name)
        }
    }
}