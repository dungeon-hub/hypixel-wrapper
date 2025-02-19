package net.dungeonhub.hypixel.entities.skyblock.dungeon

enum class CatacombsClass(val apiName: String) {
    Healer("healer"),
    Mage("mage"),
    Berserk("berserk"),
    Archer("archer"),
    Tank("tank");

    companion object {
        fun fromApiName(apiName: String): CatacombsClass? = entries.firstOrNull { it.apiName == apiName }
    }
}