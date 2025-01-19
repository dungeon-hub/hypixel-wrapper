package net.dungeonhub.hypixel.service

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

object FormattingService {
    /**
     * This formats a decimal number as a String.
     */
    fun makeDoubleReadable(number: Double, maxFractionDigits: Int = 340, locale: Locale = Locale.US): String {
        val df = DecimalFormat("0", DecimalFormatSymbols.getInstance(locale))
        df.setMaximumFractionDigits(maxFractionDigits) //340 = DecimalFormat.DOUBLE_FRACTION_DIGITS

        return df.format(number)
    }

    /**
     * Returns a (large) number as a readable String.
     * Numbers < 1000 are returned as is, simply formatted as a String.
     * Numbers >= 1000 are returned with the respective extension:
     * 1312.1852 -> "1.3121852k"
     * 3882761 -> "3.882761m"
     * 45544000000 -> "45.544b"
     */
    fun makeNumberReadable(number: Long, maxFractionDigits: Int = 340): String {
        if (number >= 1000000000000L) {
            return makeDoubleReadable(number / 1000000000000.0, maxFractionDigits) + "t"
        }

        if (number >= 1000000000L) {
            return makeDoubleReadable(number / 1000000000.0, maxFractionDigits) + "b"
        }

        if (number >= 1000000L) {
            return makeDoubleReadable(number / 1000000.0, maxFractionDigits) + "m"
        }

        if (number >= 1000L) {
            return makeDoubleReadable(number / 1000.0, maxFractionDigits) + "k"
        }

        return number.toString()
    }
}