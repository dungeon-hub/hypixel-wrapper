package net.dungeonhub.hypixel.entities

enum class CatacombsClass(val apiName: String) {
    Healer("healer"),
    Mage("mage"),
    Berserk("berserk"),
    Archer("archer"),
    Tank("tank");

    companion object {
        fun fromApiName(apiName: String): CatacombsClass? {
            return entries.firstOrNull { it.apiName == apiName }
        }
    }
}