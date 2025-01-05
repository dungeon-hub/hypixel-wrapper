package net.dungeonhub.hypixel.entities

enum class KnownEssenceType(override val apiName: String, val displayName: String) : EssenceType {
    Wither("WITHER", "Wither"),
    Undead("UNDEAD", "Undead"),
    Dragon("DRAGON", "Dragon"),
    Diamond("DIAMOND", "Diamond"),
    Spider("SPIDER", "Spider"),
    Gold("GOLD", "Gold"),
    Ice("ICE", "Ice"),
    Crimson("CRIMSON", "Crimson");

    class UnknownEssenceType(override val apiName: String) : EssenceType

    companion object {
        fun String.toEssenceType(): EssenceType {
            return KnownEssenceType.entries.firstOrNull { it.apiName == this }
                ?: UnknownEssenceType(this)
        }
    }
}