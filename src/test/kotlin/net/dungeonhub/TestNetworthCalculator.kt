package net.dungeonhub

import com.google.gson.JsonObject
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkAll
import net.dungeonhub.hypixel.entities.inventory.items.Armor
import net.dungeonhub.hypixel.entities.inventory.items.SkyblockItem
import net.dungeonhub.hypixel.entities.skyblock.price.PriceHelper
import net.dungeonhub.provider.GsonProvider
import net.dungeonhub.service.TestHelper
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import java.util.*
import kotlin.math.roundToLong
import kotlin.test.Test
import kotlin.test.assertEquals

class TestNetworthCalculator {
    @Test
    fun testNetworthCalculation() {
        val profileNetworth = mapOf(
            "Pear" to 0L,
            "Grapes" to 132L,
            "Pomegranate" to 15_810_413L,
            "Blueberry" to 163_049_696L
        )

        val profiles = TestHelper.readAllSkyblockProfileObjects()
            .filter { it.owner == UUID.fromString("39642ffc-a7fb-4d24-a1d4-916f4cad1d98") }.findFirst().orElseThrow()

        for (profile in profiles.profiles) {
            var networth = 0L

            for (member in profile.currentMembers) {
                if (member.uuid != UUID.fromString("39642ffc-a7fb-4d24-a1d4-916f4cad1d98")) continue

                networth += member.coins.toDouble().roundToLong()

                val itemPrices = mutableMapOf<String, Long>()

                for (items in member.inventory?.allItems ?: emptyList()) {
                    for (item in items.items) {
                        if (item is SkyblockItem) {
                            val itemPrice = PriceHelper.getPrice(item)

                            if (itemPrice > 1_000_000 && item is Armor) {
                                itemPrices[item.rawName] = itemPrice
                            }

                            networth += itemPrice
                        }
                    }
                }

                if (profile.cuteName == "Blueberry") {
                    println(itemPrices.entries.sortedBy { it.value }.joinToString("\n"))
                    println()
                    println("Total price: ${itemPrices.values.sum()}")
                }
            }

            assertEquals(
                profileNetworth[profile.cuteName],
                networth,
                "Networth for profile ${profile.cuteName} doesn't match!"
            )
        }
    }

    companion object {
        val itemPrices =
            GsonProvider.gson.fromJson(TestHelper.readFile("skyhelper_networth.json"), JsonObject::class.java)
                .entrySet()
                .associate { it.key to it.value.asLong }

        @BeforeAll
        @JvmStatic
        fun preparePriceHelper() {
            mockkObject(PriceHelper)

            every { PriceHelper.getBasePrice(any<String>()) }.answers {
                val apiName = it.invocation.args[0] as? String

                if (!itemPrices.containsKey(apiName)) {
                    //println("Unknown API name: $apiName")
                }

                itemPrices[apiName] ?: 0
            }
        }

        @AfterAll
        @JvmStatic
        fun tearDownPriceHelper() {
            unmockkAll()
        }
    }
}