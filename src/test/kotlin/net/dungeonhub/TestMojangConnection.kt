package net.dungeonhub

import net.dungeonhub.mojang.connection.MojangConnection
import net.dungeonhub.service.TestHelper
import net.dungeonhub.service.TestHelper.toMockResponse
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.spy
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestMojangConnection {
    @Test
    fun testUUIDByName() {
        val notchName = "Notch"
        val notchUUID = UUID.fromString("069a79f4-44e9-4726-a5be-fca90e38aaf5")

        assertTrue(mojangConnection.cache.retrieveAll().isEmpty())

        assertEquals(notchUUID, mojangConnection.getUUIDByName("notch"))
        assertEquals(notchUUID, mojangConnection.getUUIDByName("NOTCH"))

        assertEquals(1, mojangConnection.cache.retrieveAll().size)
        assertEquals(notchName, mojangConnection.cache.retrieve(notchUUID)?.name)
    }

    companion object {
        private val mojangConnection: MojangConnection = spy {
            on { getHttpClient() }.thenAnswer {
                mock<OkHttpClient> {
                    on { newCall(any()) }.thenAnswer {
                        mock<Call> {
                            on { execute() }.thenReturn(
                                TestHelper.readFile("example_mojang_data.json").toMockResponse()
                            )
                        }
                    }
                }
            }
        }
    }
}