package net.dungeonhub.mojang.entity

import java.awt.Color

enum class FormattingCode(val type: FormattingCodeType, val formattingCode: String, val color: Color? = null) {
    Black(FormattingCodeType.Color, "§0", Color.BLACK),
    DarkBlue(FormattingCodeType.Color, "§1", Color(0, 0, 170)),
    DarkGreen(FormattingCodeType.Color, "§2", Color(0, 128, 0)),
    DarkAqua(FormattingCodeType.Color, "§3", Color(0, 170, 170)),
    DarkRed(FormattingCodeType.Color, "§4", Color(170, 0, 0)),
    DarkPurple(FormattingCodeType.Color, "§5", Color(170, 0, 170)),
    Gold(FormattingCodeType.Color, "§6", Color(255, 170, 0)),
    Gray(FormattingCodeType.Color, "§7", Color(170, 170, 170)),
    DarkGray(FormattingCodeType.Color, "§8", Color(85, 85, 85)),
    Blue(FormattingCodeType.Color, "§9", Color(85, 85, 255)),
    Green(FormattingCodeType.Color, "§a", Color(60, 230, 60)),
    Aqua(FormattingCodeType.Color, "§b", Color(60, 230, 230)),
    Red(FormattingCodeType.Color, "§c", Color(255, 85, 85)),
    LightPurple(FormattingCodeType.Color, "§d", Color(255, 85, 255)),
    Yellow(FormattingCodeType.Color, "§e", Color(255, 255, 85)),
    White(FormattingCodeType.Color, "§f", Color.WHITE),

    Bold(FormattingCodeType.Formatting, "§l"),
    Strikethrough(FormattingCodeType.Formatting, "§m"),
    Underline(FormattingCodeType.Formatting, "§n"),
    Italic(FormattingCodeType.Formatting, "§o"),

    Reset(FormattingCodeType.Special, "§r"),
    Magic(FormattingCodeType.Special, "§k")
}