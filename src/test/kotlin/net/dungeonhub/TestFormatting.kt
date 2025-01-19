package net.dungeonhub

import net.dungeonhub.hypixel.service.FormattingService
import kotlin.test.Test
import kotlin.test.assertEquals

class TestFormatting {
    @Test
    fun testNumberFormatting() {
        assertEquals("19.04m", FormattingService.makeNumberReadable(19042194, 2))
        assertEquals("19m", FormattingService.makeNumberReadable(19042194, 1))
        assertEquals("1b", FormattingService.makeNumberReadable(1000000000))
        assertEquals("1.978m", FormattingService.makeNumberReadable(1977531, 3))
        assertEquals("1.978m", FormattingService.makeNumberReadable(1977631, 3))
        assertEquals("1.977m", FormattingService.makeNumberReadable(1977431, 3))
    }
}