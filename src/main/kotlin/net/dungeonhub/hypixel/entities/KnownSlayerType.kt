package net.dungeonhub.hypixel.entities

enum class KnownSlayerType(override val apiName: String): SlayerType {
    Zombie("zombie"),
    Spider("spider"),
    Wolf("wolf"),
    Enderman("enderman"),
    Blaze("blaze"),
    Vampire("vampire");

    class UnknownSlayerType(override val apiName: String): SlayerType

    companion object {
        fun fromApiName(name: String): SlayerType {
            return KnownSlayerType.entries.firstOrNull { it.apiName == name }
                ?: UnknownSlayerType(name)
        }
    }
}