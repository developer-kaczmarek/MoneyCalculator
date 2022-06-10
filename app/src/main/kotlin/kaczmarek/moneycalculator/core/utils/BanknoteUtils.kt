package kaczmarek.moneycalculator.core.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

fun Double.toFormattedAmount(): String {
    val separator = DecimalFormatSymbols(Locale.getDefault()).apply {
        groupingSeparator = ' '
        decimalSeparator = '.'
    }
    val decimalFormat = DecimalFormat().apply {
        isGroupingUsed = true
        groupingSize = 3
        decimalFormatSymbols = separator
    }
    return decimalFormat.format(this)
}