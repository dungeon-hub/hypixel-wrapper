package net.dungeonhub.hypixel.entities

enum class KnownCurrencyTypes(override val apiName: String, val displayName: String) : CurrencyType {
    Coins("coin_purse", "Coins"),
    Motes("motes_purse", "Motes");

    class UnknownCurrencyType(override val apiName: String) : CurrencyType

    companion object {
        fun String.toCurrencyType(): CurrencyType {
            return KnownCurrencyTypes.entries.firstOrNull { it.apiName == this }
                ?: UnknownCurrencyType(this)
        }
    }
}