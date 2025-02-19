package net.dungeonhub.hypixel.entities.inventory

import net.dungeonhub.mojang.entity.FormattingCode

enum class SkyblockRarity(val apiName: String, val color: FormattingCode) {
    Common("COMMON", FormattingCode.White),
    Uncommon("UNCOMMON", FormattingCode.Green),
    Rare("RARE", FormattingCode.Blue),
    Epic("EPIC", FormattingCode.DarkPurple),
    Legendary("LEGENDARY", FormattingCode.Gold),
    Mythic("MYTHIC", FormattingCode.LightPurple),
    Divine("DIVINE", FormattingCode.Aqua),
    Special("SPECIAL", FormattingCode.Red),
    VerySpecial("VERY_SPECIAL", FormattingCode.Red),
    Ultimate("ULTIMATE", FormattingCode.DarkRed),
    Admin("ADMIN", FormattingCode.DarkRed);

    companion object {
        fun fromApiName(apiName: String): SkyblockRarity = SkyblockRarity.entries.first { it.apiName == apiName }
    }
}