package net.dungeonhub.hypixel.entities

enum class ProfileGameMode(val apiName: String?) {
    Default(null),
    Ironman("ironman"),
    Bingo("bingo");

    companion object {
        fun fromApiName(apiName: String?): ProfileGameMode {
            return entries.firstOrNull { it.apiName == apiName } ?: Default
        }
    }
}