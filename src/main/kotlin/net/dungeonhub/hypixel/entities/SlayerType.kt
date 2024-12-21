package net.dungeonhub.hypixel.entities

enum class SlayerType(val apiName: String) {
    Zombie("zombie"),
    Spider("spider"),
    Wolf("wolf"),
    Enderman("enderman"),
    Blaze("blaze"),
    Vampire("vampire");

    companion object {
        fun fromApiName(name: String): SlayerType {
            return entries.first { it.apiName == name }
        }
    }
}