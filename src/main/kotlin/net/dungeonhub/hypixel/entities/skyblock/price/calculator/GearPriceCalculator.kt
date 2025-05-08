package net.dungeonhub.hypixel.entities.skyblock.price.calculator

import net.dungeonhub.hypixel.entities.inventory.items.Gear
import net.dungeonhub.hypixel.entities.inventory.items.id.DungeonItemId
import net.dungeonhub.hypixel.entities.skyblock.price.PriceHelper

object GearPriceCalculator : PriceCalculator<Gear> {
    override val type = Gear::class.java

    override fun calculatePrice(item: Gear): Long {
        //TODO essence cost?

        var price = 0L

        //TODO rework and maybe check for essence type -> check if its an undead / wither essence item
        if (item.dungeonItem) {
            if (item.starAmount >= 6) {
                price += PriceHelper.getAppliedPrice(DungeonItemId.FirstMasterStar)
            }

            if (item.starAmount >= 7) {
                price += PriceHelper.getAppliedPrice(DungeonItemId.SecondMasterStar)
            }

            if (item.starAmount >= 8) {
                price += PriceHelper.getAppliedPrice(DungeonItemId.ThirdMasterStar)
            }

            if (item.starAmount >= 9) {
                price += PriceHelper.getAppliedPrice(DungeonItemId.FourthMasterStar)
            }

            if (item.starAmount >= 10) {
                price += PriceHelper.getAppliedPrice(DungeonItemId.FifthMasterStar)
            }
        }

        return price
    }
}