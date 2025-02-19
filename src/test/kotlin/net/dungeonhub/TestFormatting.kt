package net.dungeonhub

import net.dungeonhub.hypixel.service.FormattingService
import kotlin.test.Test
import kotlin.test.assertEquals

class TestFormatting {
    @Test
    fun testNumberFormatting() {
        assertEquals("19.04m", FormattingService.makeNumberReadable(19_042_194, 2))
        assertEquals("19m", FormattingService.makeNumberReadable(19_042_194, 1))
        assertEquals("1b", FormattingService.makeNumberReadable(1_000_000_000))
        assertEquals("1.978m", FormattingService.makeNumberReadable(1_977_531, 3))
        assertEquals("1.978m", FormattingService.makeNumberReadable(1_977_631, 3))
        assertEquals("1.977m", FormattingService.makeNumberReadable(1_977_431, 3))
    }
}