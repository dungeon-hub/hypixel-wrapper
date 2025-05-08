package net.dungeonhub.hypixel.entities.skyblock.price.calculator

import net.dungeonhub.hypixel.entities.inventory.items.id.WeaponItemId
import net.dungeonhub.hypixel.entities.inventory.items.special.MidasWeapon
import kotlin.math.roundToLong

object MidasWeaponPriceCalculator : PriceCalculator<MidasWeapon> {
    override val type = MidasWeapon::class.java

    const val MIDAS_COIN_WORTH = 0.65

    val maxBids = mapOf(
        WeaponItemId.MidasSword to 50_000_000,
        WeaponItemId.MidasStaff to 100_000_000,
        WeaponItemId.UpgradedMidasSword to 250_000_000,
        WeaponItemId.UpgradedMidasStaff to 500_000_000
    )

    override fun calculatePrice(item: MidasWeapon): Long {
        val maxBid = maxBids[item.id] ?: return 0

        val pricePaid = if (item.pricePaid >= maxBid)
            maxBid
        else
            item.pricePaid

        return (pricePaid * MIDAS_COIN_WORTH).roundToLong()
    }
}