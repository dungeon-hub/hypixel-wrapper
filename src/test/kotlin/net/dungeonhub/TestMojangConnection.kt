package net.dungeonhub

import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkAll
import net.dungeonhub.mojang.connection.MojangConnection
import net.dungeonhub.service.TestHelper
import net.dungeonhub.service.TestHelper.toMockResponse
import okhttp3.Call
import okhttp3.OkHttpClient
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestMojangConnection {
    @Test
    fun testUUIDByName() {
        val notchName = "Notch"
        val notchUUID = UUID.fromString("069a79f4-44e9-4726-a5be-fca90e38aaf5")

        assertTrue(MojangConnection.cache.retrieveAll().findAny().isEmpty)

        assertEquals(notchUUID, MojangConnection.getUUIDByName("notch"))
        assertEquals(notchUUID, MojangConnection.getUUIDByName("NOTCH"))

        assertEquals(1, MojangConnection.cache.retrieveAll().count())
        assertEquals(notchName, MojangConnection.cache.retrieve(notchUUID)?.name)
    }

    companion object {
        @BeforeAll
        @JvmStatic
        fun prepareMojangConnection() {
            mockkObject(MojangConnection)

            every { MojangConnection.getHttpClient() }.returns(
                mock<OkHttpClient> {
                    on { newCall(any()) }.thenAnswer {
                        mock<Call> {
                            on { execute() }.thenReturn(
                                TestHelper.readFile("example_mojang_data.json").toMockResponse()
                            )
                        }
                    }
                }
            )
        }

        @AfterAll
        @JvmStatic
        fun tearDownMojangConnection() {
            unmockkAll()
        }
    }
}