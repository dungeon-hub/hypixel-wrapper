package net.dungeonhub.hypixel.entities

//TODO complete mapping
enum class KnownPetItem(override val apiName: String, displayName: String? = null) : PetItem {
    DwarfTurtleShelmet("DWARF_TURTLE_SHELMET"),
    ExpShare("PET_ITEM_EXP_SHARE"),
    LuckyClover("PET_ITEM_LUCKY_CLOVER"),
    MinosRelic("MINOS_RELIC"),
    CrochetTigerPlushie("CROCHET_TIGER_PLUSHIE");

    val displayName = displayName ?: name.replace(Regex("([A-Z])"), " $1").trim()

    class UnknownPetItem(override val apiName: String) : PetItem

    companion object {
        fun fromApiName(apiName: String): PetItem {
            return KnownPetItem.entries.firstOrNull { it.apiName == apiName }
                ?: UnknownPetItem(apiName)
        }
    }
}