package net.dungeonhub.hypixel.entities.player

import com.google.gson.JsonObject
import net.dungeonhub.mojang.entity.FormattingCode
import net.dungeonhub.provider.getAsJsonPrimitiveOrNull

enum class KnownRank(
    override val apiName: String,
    private val prefix: String,
    val color: FormattingCode,
    override val isSpecial: Boolean = false,
    override val isStaff: Boolean = false
) : Rank {
    Default("NONE", "§7", FormattingCode.Gray),
    Vip("VIP", "§a[VIP]", FormattingCode.Green),
    VipPlus("VIP_PLUS", "§a[VIP§6+§a]", FormattingCode.Green),
    Mvp("MVP", "§b[MVP]", FormattingCode.Aqua),
    MvpPlus("MVP_PLUS", "§b[MVP§c+§b]", FormattingCode.Aqua),
    MvpPlusPlus("SUPERSTAR", "§6[MVP§c++§6]", FormattingCode.Gold),
    YouTube("YOUTUBER", "§c[§fYOUTUBE§c]", FormattingCode.Red, isSpecial = true),
    GameMaster("GAME_MASTER", "§2[GM]", FormattingCode.DarkGreen, isSpecial = true, isStaff = true),
    Admin("ADMIN", "§c[ADMIN]", FormattingCode.Red, isSpecial = true, isStaff = true);

    val displayName = name.replace("Plus", "+")

    fun getRankPrefix(rankPlusColor: String?, superStarColor: String?): String {
        if (rankPlusColor != null) {
            if (MvpPlusPlus == this) {
                val actualSuperStarColor = superStarColor ?: FormattingCode.Gold.formattingCode
                return "$actualSuperStarColor[MVP$rankPlusColor++$actualSuperStarColor]"
            }

            if (MvpPlus == this) {
                return "§b[MVP$rankPlusColor+§b]"
            }
        }

        return prefix
    }

    class UnknownRank(
        override val apiName: String,
        override val isSpecial: Boolean = false,
        override val isStaff: Boolean = false
    ) : Rank

    companion object {
        fun fromApiName(apiName: String): Rank =
            KnownRank.entries.firstOrNull { it.apiName == apiName } ?: UnknownRank(apiName)

        fun fromPlayerObject(jsonObject: JsonObject): Rank {
            val rank = jsonObject.getAsJsonPrimitiveOrNull("rank")?.let { fromApiName(it.asString) }

            if (rank?.isSpecial == true) {
                return rank
            }

            val monthlyRank = jsonObject.getAsJsonPrimitiveOrNull("monthlyPackageRank")
                ?.let { fromApiName(it.asString) }

            if (monthlyRank != null && monthlyRank != Default) {
                return monthlyRank
            }

            val packageRank = jsonObject.getAsJsonPrimitiveOrNull("newPackageRank")
                ?.let { fromApiName(it.asString) }

            return packageRank ?: Default
        }
    }
}