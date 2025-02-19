package net.dungeonhub

import net.dungeonhub.mojang.entity.FormattingCode
import net.dungeonhub.mojang.entity.FormattingCodeType
import kotlin.test.*

class TestFormattingCodes {
    @Test
    fun testCorrectFormattingCodes() {
        FormattingCode.entries.forEach {
            assertTrue { it.formattingCode.startsWith("§") }
            assertEquals(2, it.formattingCode.length)

            if (it.type == FormattingCodeType.Color) {
                assertNotNull(it.color)
            } else {
                assertNull(it.color)
            }
        }
    }
}