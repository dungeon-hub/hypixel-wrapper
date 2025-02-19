package net.dungeonhub.hypixel.entities.skyblock.misc

enum class ProfileGameMode(val apiName: String?) {
    Default(null),
    Ironman("ironman"),
    Bingo("bingo");

    companion object {
        fun fromApiName(apiName: String?): ProfileGameMode = entries.firstOrNull { it.apiName == apiName } ?: Default
    }
}