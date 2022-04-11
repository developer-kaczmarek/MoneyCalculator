package kaczmarek.moneycalculator.core.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

fun Double.toFormattedAmount(): String {
    return if (this >= 0.0) {
        val separator = DecimalFormatSymbols(Locale.getDefault()).apply {
            groupingSeparator = ' '
        }
        val decimalFormat = DecimalFormat().apply {
            isGroupingUsed = true
            groupingSize = 3
            decimalFormatSymbols = separator
        }
        decimalFormat.format(this)
    } else {
        this.toString()
    }
}