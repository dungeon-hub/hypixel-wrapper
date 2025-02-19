package net.dungeonhub.hypixel.service

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

object FormattingService {
    const val THOUSAND = 1_000.0
    const val MILLION = 1_000_000.0
    const val BILLION = 1_000_000_000.0
    const val TRILLION = 1_000_000_000_000.0

    /**
     * This formats a decimal number as a String.
     */
    fun makeDoubleReadable(number: Double, maxFractionDigits: Int = 340, locale: Locale = Locale.US): String {
        val df = DecimalFormat("0", DecimalFormatSymbols.getInstance(locale))
        df.setMaximumFractionDigits(maxFractionDigits) // 340 = DecimalFormat.DOUBLE_FRACTION_DIGITS

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
        if (number >= TRILLION) {
            return makeDoubleReadable(number / TRILLION, maxFractionDigits) + "t"
        }

        if (number >= BILLION) {
            return makeDoubleReadable(number / BILLION, maxFractionDigits) + "b"
        }

        if (number >= MILLION) {
            return makeDoubleReadable(number / MILLION, maxFractionDigits) + "m"
        }

        if (number >= THOUSAND) {
            return makeDoubleReadable(number / THOUSAND, maxFractionDigits) + "k"
        }

        return number.toString()
    }
}