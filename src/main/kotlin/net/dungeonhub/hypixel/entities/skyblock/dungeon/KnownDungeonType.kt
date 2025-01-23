package net.dungeonhub.hypixel.entities.skyblock.dungeon

enum class KnownDungeonType(override val apiName: String) : DungeonType {
    Catacombs("catacombs"),
    MasterCatacombs("master_catacombs");

    class UnknownDungeonType(override val apiName: String) : DungeonType

    companion object {
        fun fromApiName(apiName: String): DungeonType {
            return entries.firstOrNull {
                it.apiName == apiName
            } ?: UnknownDungeonType(apiName)
        }
    }
}