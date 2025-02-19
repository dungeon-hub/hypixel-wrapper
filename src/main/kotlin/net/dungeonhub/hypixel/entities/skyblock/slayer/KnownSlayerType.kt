package net.dungeonhub.hypixel.entities.skyblock.slayer

enum class KnownSlayerType(override val apiName: String, val requiredExperience: List<Int>) : SlayerType {
    Zombie("zombie", listOf(5, 15, 200, 1_000, 5_000, 20_000, 100_000, 400_000, 1_000_000)),
    Spider("spider", listOf(5, 25, 200, 1_000, 5_000, 20_000, 100_000, 400_000, 1_000_000)),
    Wolf("wolf", listOf(10, 30, 250, 1_500, 5_000, 20_000, 100_000, 400_000, 1_000_000)),
    Enderman("enderman", listOf(10, 30, 250, 1_500, 5_000, 20_000, 100_000, 400_000, 1_000_000)),
    Blaze("blaze", listOf(10, 30, 250, 1_500, 5_000, 20_000, 100_000, 400_000, 1_000_000)),
    Vampire("vampire", listOf(20, 75, 240, 840, 2_400));

    fun toLevel(xp: Int): Int {
        for ((index, requiredXp) in requiredExperience.withIndex()) {
            if (xp < requiredXp) return index
        }

        return requiredExperience.size
    }

    class UnknownSlayerType(override val apiName: String) : SlayerType

    companion object {
        fun fromApiName(name: String): SlayerType {
            return KnownSlayerType.entries.firstOrNull { it.apiName == name }
                ?: UnknownSlayerType(name)
        }
    }
}